package graphical.basics.presentation;

import codec.*;
import codec.engine.JavaFXEngine;
import codec.engine.JavaGraphicEngine;
import codec.engine.JavaNativeEngine;
import graphical.basics.BackGround;
import graphical.basics.gobject.Camera;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.task.*;
import graphical.basics.task.transformation.gobject.ColorTranform;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public abstract class Animation extends AnimationStaticReference {

    public static Animation staticReference;

    private boolean switchProcessing = true;
    private boolean disableCodec;
    private boolean isDisablePreview;
    private boolean isEnableTransparency;

    public static int FRAME_RATE = 60;

    private JFrame frame;


    //  public BufferedImage bufferedImage;
    public Graphics bufferedGraphics;

    VideoCodec videoCodec;
    JavaGraphicEngine graphicEngine;

    List<Gobject> gobjects = new ArrayList<>();
    private List<Runnable> prePaintTasks = new ArrayList<>();

    //FpsControler fpsControler = new FpsControler();

    int clipCounter = 0;
    int frameCounter = 0;

    long lastMesure = System.currentTimeMillis();


    private final BackGround backGround;
    private Camera camera;

    private final PresentationConfig presentationConfig = new PresentationConfig();

    public final EndLessParallelTask backGroundTask = new EndLessParallelTask();

    public Animation() {

        setup(presentationConfig);
        applyConfigs(presentationConfig);

        backGround = new BackGround(presentationConfig.getWidth(), presentationConfig.getHeight());


        //dirs
        var executionPath = System.getProperty("user.dir");
        File videoDir = new File(executionPath + "/video");
        videoDir.mkdir();

        File rawDir = new File(executionPath + "/video/raw");
        rawDir.mkdir();

        if (!disableCodec)
            videoCodec.startNewVideo(executionPath + "/video/", "mv" + clipCounter + videoCodec.getFileFormat(), FRAME_RATE);


        //preview window


        staticReference = this;
        AnimationStaticReference.staticReference = this;

    }

    public abstract void setup(PresentationConfig presentationConfig);

    private void applyConfigs(PresentationConfig presentationConfig) {
        if (presentationConfig.getFramerate() != null) {
            FRAME_RATE = presentationConfig.getFramerate();
        } else {
            FRAME_RATE = 30;
        }

        if (presentationConfig.isDisableCodec() != null) {
            disableCodec = presentationConfig.isDisableCodec();
        } else {
            disableCodec = false;
        }

        if (presentationConfig.getCodec() != null) {
            switch (presentationConfig.getCodec()) {
                case JCODEC:
                    this.videoCodec = new JCodec();
                    break;
                case RAW_IMAGE:
                    this.videoCodec = new RawImageCodec();
                    break;
                case XUGGLE:
                    this.videoCodec = new XugglerCodec(presentationConfig);
                    break;
                case GIF:
                    this.videoCodec = new GIFCodec();
            }

        } else {
            this.videoCodec = new XugglerCodec(presentationConfig);//default
        }


        this.camera = new Camera(Location.at((presentationConfig.getHeight() / 2.0), presentationConfig.getWidth() / 2.0), presentationConfig.getScale());

        if (!presentationConfig.isDisablePreview()) {

            frame = new JFrame() {
                @Override
                public void paint(Graphics g) {
                    if (graphicEngine != null)
                        g.drawImage(graphicEngine.getActualFrame(), 0, 0, null);
                    g.setColor(Color.green);
                    g.drawString("" + frameCounter, 900, 100);
                    g.drawString((System.currentTimeMillis() - lastMesure) + " ms", 900, 150);
                    lastMesure = System.currentTimeMillis();
                }
            };

            //preview windowSize
            frame.setUndecorated(!presentationConfig.isPreviewWindowBarVisible());
            frame.setSize((int) (presentationConfig.getWidth() * presentationConfig.getScale()), (int) (presentationConfig.getHeight() * presentationConfig.getScale()));
            //eable preview

            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setTitle(" Saturn-preview ");
//
//            try {
//                var icn = ImageIO.read(new File("C:\\Users\\PICHAU\\Desktop\\saticon2.png"));
//                frame.setIconImage(icn);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else {
            isDisablePreview = true;
        }


        if (presentationConfig.getEngine() != null) {
            switch (presentationConfig.getEngine()) {
                case NATIVE_JAVA:
                    graphicEngine = new JavaNativeEngine((int) (presentationConfig.getWidth() * presentationConfig.getScale()), (int) (presentationConfig.getHeight() * presentationConfig.getScale()));
                    break;
                case JAVAFX:
                    graphicEngine = new JavaFXEngine((int) (presentationConfig.getWidth() * presentationConfig.getScale()), (int) (presentationConfig.getHeight() * presentationConfig.getScale()));
                    break;
            }
        } else {
            graphicEngine = new JavaNativeEngine((int) (presentationConfig.getWidth() * presentationConfig.getScale()), (int) (presentationConfig.getHeight() * presentationConfig.getScale()));

        }
        // this.bufferedImage = graphicEngine.getActualFrame();
        bufferedGraphics = graphicEngine.getGraphics();

        isEnableTransparency = presentationConfig.isEnableTransparency();

    }

    protected abstract void buildAnimation();

    public void build() {
        buildAnimation();
        joinBackGroundTasks();
        execute(wait(1));
        cut();
    }

    public void joinBackGroundTasks() {
        if (backGroundTask.hasTasks()) {
            // consome as coisas que estejam ainda em background
            var remainingTaks = backGroundTask.getResumedTask();
            backGroundTask.clear();
            remainingTaks.execute();
        }
    }


    public void processFrame() {
        for (int i = 0; i < prePaintTasks.size(); i++) {
            prePaintTasks.get(i).run();
        }
        frameCounter++;
        paintComponent(bufferedGraphics);
        if (!isDisablePreview)
            frame.repaint();
        var before2 = System.currentTimeMillis();
        if (!disableCodec) videoCodec.addFrame(graphicEngine.getActualFrame());
        if (disableCodec) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        if (isEnableTransparency)
            graphicEngine.clear();
        backGround.paint(g);


        var g2d = (Graphics2D) g;
        var oldT = (AffineTransform) g2d.getTransform().clone();
        camera.applyView(g);

        for (int i = 0; i < gobjects.size(); i++) {
            gobjects.get(i).paint(g, true);
        }

        g2d.setTransform(oldT);
    }


    public void add(Gobject gobject) {
        gobjects.add(gobject);
    }

    public void add(Gobject... gs) {
        gobjects.addAll(Arrays.asList(gs));
    }

    public void addBefore(Gobject referential, Gobject gobject) {
        gobjects.add(gobjects.indexOf(referential), gobject);
    }

    public void remove(Gobject gobject) {
        gobjects.remove(gobject);
    }

    public int getObjectIndex(Gobject gobject) {
        return gobjects.indexOf(gobject);
    }

    public void add(Gobject g, int index) {
        gobjects.add(index, g);
    }

    public void remove(Gobject... gobjects) {
        for (Gobject gobject : gobjects) {
            this.gobjects.remove(gobject);
        }
    }

    public void removeAll() {
        this.gobjects = new ArrayList<>();
    }

    public void execute(Task task) {

        task.setup();
        while (!task.isDone()) {
            task.step();
            backGroundTask.step();
            if (switchProcessing)
                processFrame();
        }
        task.shutDown();
        //cut();
    }

    public InterruptableTask executeInBackGround(Task task) {
        return backGroundTask.append(task);
    }

    public void execute(Task... tasks) {
        execute(new ParalelTask(tasks));
    }

    public void cut() {

        // wait(seconds(10)).execute();

        if (!disableCodec) {
            videoCodec.saveVideo();
            clipCounter++;
            videoCodec.startNewVideo("video/", "mv" + clipCounter + ".mov", FRAME_RATE);
        }
    }


    public int seconds(double seconds) {
        return (int) (seconds * FRAME_RATE);
    }

    public Task paralel(Task... tasks) {
        return new ParalelTask(tasks);
    }

    public Task fadeOut(Gobject gobject, int steps) {
        return new ColorTranform(gobject, new Color(0, 0, 0, 0), steps);
    }

    public Task fadeOut(Gobject gobject) {
        return new ColorTranform(gobject, new Color(0, 0, 0, 0), seconds(1));
    }

    public Task wait(int steps) {
        return new WaitTask(steps);
    }


    public void switchOff() {
        switchProcessing = false;
    }

    public void switchOn() {
        switchProcessing = true;
    }


    public BackGround getBackGround() {
        return backGround;
    }

    public Camera getCamera() {
        return camera;
    }

    public PresentationConfig getPresentationConfig() {
        return presentationConfig;
    }

    public List<Gobject> getGobjects() {
        return gobjects;
    }

    public Runnable addBehavior(Runnable task) {
        prePaintTasks.add(task);

        return task;
    }

    public <T> Runnable addBehavior(T metadata, Consumer<T> task) {
        return addBehavior(() -> task.accept(metadata));
    }

    public void removeBehavior(Runnable r) {
        prePaintTasks.remove(r);
    }

    @Override
    public void clear() {
        gobjects = new ArrayList<>();
        prePaintTasks = new ArrayList<>();
    }

}
