package graphical.basics.presentation;

import codec.JCodec;
import codec.VideoCodec;
import graphical.basics.behavior.Behavior;
import graphical.basics.gobject.Gobject;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.transformation.gobject.ColorTranform;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Presentation extends JFrame {

    public static int FRAME_RATE = 60;
    public static Presentation staticReference;

    private boolean disableCodec;

    BufferedImage bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
    public Graphics bufferedGraphics = bufferedImage.getGraphics();

    VideoCodec videoCodec = new JCodec();
    //VideoCodec videoCodec = new JaveEncoder();
    List<Gobject> gobjects = new ArrayList<>();
    List<Behavior> behaviors = new ArrayList<>();

    FpsControler fpsControler = new FpsControler();

    int clipCounter = 0;
    int frameCounter = 0;

    long lastMesure = System.currentTimeMillis();

    Task t;


    public Presentation() {
        // ((Graphics2D)bufferedGraphics).scale(1.5,1.5);

        videoCodec.startNewVideo("", "pepe" + clipCounter + ".mov", FRAME_RATE);


        Graphics2D g2 = (Graphics2D) bufferedGraphics;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        //preview window

        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);

        // setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
        setVisible(true);


        staticReference = this;

        PresentationConfig presentationConfig = new PresentationConfig();
        setup(presentationConfig);
        applyConfigs(presentationConfig);

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

    }

    public abstract void buildPresentation();

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, 0, null);
        g.setColor(Color.green);
        g.drawString("" + frameCounter, 900, 100);
        g.drawString((System.currentTimeMillis() - lastMesure) + " ms", 900, 150);
        lastMesure = System.currentTimeMillis();
//        g.setColor(Color.white);
//        g.fillRect(0, 0, 1000, 1000);

        // TaskPainter.paint(g,t,500,200);
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

        if(disableCodec) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {

        // background;
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 1000, 1000);
//        int[] pixels = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
//
//        Arrays.fill(pixels,0);
//        bufferedImage.setRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth());
//

        for (int i = 0; i < gobjects.size(); i++) {
            gobjects.get(i).paint(g);
        }
    }

    public void runBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.update();
        }
    }

    //
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
        t = task;
        task.setup();
        while (!task.isDone()) {
            task.step();
            processFrame();
        }
    }

    public void execute(Task... tasks) {
        execute(new ParalelTask(tasks));
    }

    public void cut() {
        videoCodec.saveVideo();
        clipCounter++;
        videoCodec.startNewVideo("", "pepe" + clipCounter + ".mov", FRAME_RATE);
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

}
