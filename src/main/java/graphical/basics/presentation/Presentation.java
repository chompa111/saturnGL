package graphical.basics.presentation;

import codec.JCodec;
import codec.RawImageCodec;
import codec.VideoCodec;
import codec.XugglerCodec;
import codec.engine.JavaFXEngine;
import codec.engine.JavaGraphicEngine;
import codec.engine.JavaNativeEngine;
import graphical.basics.BackGround;
import graphical.basics.behavior.Behavior;
import graphical.basics.gobject.Camera;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.task.EndLessParallelTask;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorTranform;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Presentation extends JFrame {

    public static Presentation staticReference;

    private boolean switchProcessing = true;
    private boolean disableCodec;

    public static int FRAME_RATE = 60;


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


    private BackGround backGround;
    private final Camera camera = new Camera();

    public final EndLessParallelTask backGroundTask = new EndLessParallelTask();

    public Presentation() {


        PresentationConfig presentationConfig = new PresentationConfig();
        setup(presentationConfig);
        applyConfigs(presentationConfig);

        backGround = new BackGround(presentationConfig.getWidth(), presentationConfig.getHeight());


        //dirs
        File videoDir = new File("video");
        videoDir.mkdir();

        File rawDir = new File("video\\raw");
        rawDir.mkdir();

        videoCodec.startNewVideo("video/", "mv" + clipCounter + ".mov", FRAME_RATE);


        //preview window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);


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
            }

        } else {
            this.videoCodec = new XugglerCodec(presentationConfig);//default
        }

        //preview windowSize
        this.setUndecorated(presentationConfig.isPreviewWindowBarVisible());
        this.setSize(presentationConfig.getWidth(), presentationConfig.getHeight());

        if(presentationConfig.getEngine()!=null){
            switch (presentationConfig.getEngine()){
                case NATIVE_JAVA:
                    graphicEngine = new JavaNativeEngine(presentationConfig.getWidth(), presentationConfig.getHeight());
                    break;
                case JAVAFX:
                    graphicEngine = new JavaFXEngine(presentationConfig.getWidth(), presentationConfig.getHeight());
                    break;
            }
        }else{
            graphicEngine = new JavaNativeEngine(presentationConfig.getWidth(), presentationConfig.getHeight());

        }
       // this.bufferedImage = graphicEngine.getActualFrame();
        bufferedGraphics = graphicEngine.getGraphics();

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


    @Override
    public void paint(Graphics g) {
        g.drawImage(graphicEngine.getActualFrame(), 0, 0, null);
        g.setColor(Color.green);
        g.drawString("" + frameCounter, 900, 100);
        g.drawString((System.currentTimeMillis() - lastMesure) + " ms", 900, 150);
        lastMesure = System.currentTimeMillis();
    }

    public void processFrame() {
        runBehaviors();
        frameCounter++;
        System.out.println("COUNTER(" + frameCounter + ")");
        var before1 = System.currentTimeMillis();
        paintComponent(bufferedGraphics);
        repaint();
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

        backGround.paint(g);


//        {
//            int[] pixels = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
//
//            Arrays.fill(pixels, 0);
//            bufferedImage.setRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth());
//        }
//
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
