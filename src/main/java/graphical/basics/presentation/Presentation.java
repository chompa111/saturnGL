package graphical.basics.presentation;

import codec.*;
import codec.engine.JavaFXEngine;
import codec.engine.JavaGraphicEngine;
import codec.engine.JavaNativeEngine;
import graphical.basics.BackGround;
import graphical.basics.behavior.Behavior;
import graphical.basics.gobject.Camera;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.task.EndLessParallelTask;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorTranform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public abstract class Presentation {

    public static Presentation staticReference;

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
    List<Behavior> behaviors = new ArrayList<>();

    //FpsControler fpsControler = new FpsControler();

    int clipCounter = 0;
    int frameCounter = 0;

    long lastMesure = System.currentTimeMillis();


    private final BackGround backGround;
    private Camera camera;

    public final EndLessParallelTask backGroundTask = new EndLessParallelTask();

    public Presentation() {


        PresentationConfig presentationConfig = new PresentationConfig();
        setup(presentationConfig);
        applyConfigs(presentationConfig);

        backGround = new BackGround(presentationConfig.getWidth(), presentationConfig.getHeight());


        //dirs
        var executionPath=System.getProperty("user.dir");
        File videoDir = new File(executionPath+"\\video");
        videoDir.mkdir();

        File rawDir = new File(executionPath+"\\video\\raw");
        rawDir.mkdir();

        if (!disableCodec)
            videoCodec.startNewVideo("video/", "mv" + clipCounter + videoCodec.getFileFormat(), FRAME_RATE);


        //preview window


        staticReference = this;

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
                case GIF:
                    this.videoCodec = new GIFCodec();
            }

        } else {
            this.videoCodec = new XugglerCodec(presentationConfig);//default
        }


        this.camera = new Camera(Location.at((presentationConfig.getHeight() / 2), presentationConfig.getWidth() / 2));

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
            frame.setSize(presentationConfig.getWidth(), presentationConfig.getHeight());
            //eable preview

            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setTitle(" Saturn-preview ");

            try {
                var icn = ImageIO.read(new File("C:\\Users\\PICHAU\\Desktop\\saticon2.png"));
                frame.setIconImage(icn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            isDisablePreview = true;
        }


        if (presentationConfig.getEngine() != null) {
            switch (presentationConfig.getEngine()) {
                case NATIVE_JAVA:
                    graphicEngine = new JavaNativeEngine(presentationConfig.getWidth(), presentationConfig.getHeight());
                    break;
                case JAVAFX:
                    graphicEngine = new JavaFXEngine(presentationConfig.getWidth(), presentationConfig.getHeight());
                    break;
            }
        } else {
            graphicEngine = new JavaNativeEngine(presentationConfig.getWidth(), presentationConfig.getHeight());

        }
        // this.bufferedImage = graphicEngine.getActualFrame();
        bufferedGraphics = graphicEngine.getGraphics();

        isEnableTransparency = presentationConfig.isEnableTransparency();

    }

    protected abstract void buildPresentation();

    public void build() {
        buildPresentation();
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
        runBehaviors();
        frameCounter++;
        System.out.println("COUNTER(" + frameCounter + ")");
        var before1 = System.currentTimeMillis();
        paintComponent(bufferedGraphics);
        if (!isDisablePreview)
            frame.repaint();
        System.out.println((System.currentTimeMillis() - before1) + "ms processando quadro");
        var before2 = System.currentTimeMillis();
        if (!disableCodec) videoCodec.addFrame(graphicEngine.getActualFrame());
        System.out.println((System.currentTimeMillis() - before1) + "ms de codec");

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

    public void runBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.update();
        }
    }

    public void add(Gobject gobject) {
        gobjects.add(gobject);
    }

    public void add(Behavior behavior) {
        behaviors.add(behavior);
    }

    public void remove(Gobject gobject) {
        gobjects.remove(gobject);
    }

    public void execute(Task task) {

        task.setup();
        while (!task.isDone()) {
            task.step();
            backGroundTask.step();
            if (switchProcessing)
                processFrame();
        }
        //cut();
    }

    public void execute(Task... tasks) {
        execute(new ParalelTask(tasks));
    }

    public void cut() {
        videoCodec.saveVideo();
        clipCounter++;
        if (!disableCodec)
            videoCodec.startNewVideo("video/", "mv" + clipCounter + ".mov", FRAME_RATE);
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

}
