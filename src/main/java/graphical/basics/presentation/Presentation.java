package graphical.basics.presentation;

import codec.JCodec;
import codec.RawImageCodec;
import codec.VideoCodec;
import graphical.basics.BackGround;
import graphical.basics.behavior.Behavior;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorTranform;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Presentation extends JFrame {

    public static Presentation staticReference;

    private boolean switchProcessing = true;
    private boolean disableCodec;

    public static int FRAME_RATE = 60;


    public BufferedImage bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
    public Graphics bufferedGraphics = bufferedImage.getGraphics();

    VideoCodec videoCodec;
    List<Gobject> gobjects = new ArrayList<>();
    List<Behavior> behaviors = new ArrayList<>();

    //FpsControler fpsControler = new FpsControler();

    int clipCounter = 0;
    int frameCounter = 0;

    long lastMesure = System.currentTimeMillis();



    private BackGround backGround = new BackGround();

    public Presentation() {


        PresentationConfig presentationConfig = new PresentationConfig();
        setup(presentationConfig);
        applyConfigs(presentationConfig);

        videoCodec.startNewVideo("video/", "mv" + clipCounter + ".mov", FRAME_RATE);


        Graphics2D g2 = (Graphics2D) bufferedGraphics;

        RenderingHints rh = new RenderingHints(

                new HashMap<>() {
                    {
                        put(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                    }
                });

        g2.setRenderingHints(rh);

        //preview window

        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
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
            }

        } else {
            this.videoCodec = new JCodec();
        }


    }

    public abstract void buildPresentation();

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, 0, null);
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
        if (!disableCodec) videoCodec.addFrame(bufferedImage);
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

        for (int i = 0; i < gobjects.size(); i++) {
            gobjects.get(i).paint(g, true);
        }
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

    public BackGround getBackGround() {
        return backGround;
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

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }
}
