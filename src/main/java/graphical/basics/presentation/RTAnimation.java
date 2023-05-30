package graphical.basics.presentation;

import codec.engine.JavaFXEngine;
import codec.engine.JavaGraphicEngine;
import codec.engine.JavaNativeEngine;
import graphical.basics.BackGround;
import graphical.basics.gobject.Camera;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.listeners.ClickListener;
import graphical.basics.listeners.DragListener;
import graphical.basics.listeners.KeyListener;
import graphical.basics.location.Location;
import graphical.basics.task.EndLessParallelTask;
import graphical.basics.task.InterruptableTask;
import graphical.basics.task.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public abstract class RTAnimation extends AnimationStaticReference {

    public static Animation staticReference;

    private boolean switchProcessing = true;
    private boolean disableCodec;
    private boolean isDisablePreview;
    private boolean isEnableTransparency;

    private DragListener dragListener;
    private ClickListener clickListener;
    private KeyListener keyListener;

    public static int FRAME_RATE = 60;

    private JFrame frame;

    public Graphics bufferedGraphics;

    JavaGraphicEngine graphicEngine;

    List<Gobject> gobjects = new ArrayList<>();
    private final List<Runnable> prePaintTasks = new ArrayList<>();

    long lastMesure = System.currentTimeMillis();

    private BackGround backGround;
    private Camera camera;

    private final PresentationConfig presentationConfig = new PresentationConfig();

    public final EndLessParallelTask backGroundTask = new EndLessParallelTask();

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

        this.camera = new Camera(Location.at((presentationConfig.getHeight() / 2.0), presentationConfig.getWidth() / 2.0), presentationConfig.getScale());


        frame = new JFrame() {
            @Override
            public void paint(Graphics g) {
                if (graphicEngine != null)
                    g.drawImage(graphicEngine.getActualFrame(), 0, 0, null);
                lastMesure = System.currentTimeMillis();
            }
        };
        dragListener = new DragListener(frame);
        clickListener = new ClickListener(frame);
        keyListener = new KeyListener(frame);


        //preview windowSize
        frame.setUndecorated(!presentationConfig.isPreviewWindowBarVisible());
        frame.setSize((int) (presentationConfig.getWidth() * presentationConfig.getScale()), (int) (presentationConfig.getHeight() * presentationConfig.getScale()));
        //eable preview

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

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


    }


    public abstract void buildAnimation();

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

    public void processFrame() {
        dragListener.perform();
        for (int i = 0; i < prePaintTasks.size(); i++) {
            prePaintTasks.get(i).run();
        }
        paintComponent(bufferedGraphics);
        frame.repaint();
    }

    public RTAnimation() {

        AnimationStaticReference.staticReference = this;
        setup(presentationConfig);
        applyConfigs(presentationConfig);

        backGround = new BackGround(presentationConfig.getWidth(), presentationConfig.getHeight());

        new Thread(() -> {
            while (true) {
                backGroundTask.step();
                processFrame();
                try {
                    Thread.sleep(25);
                    //System.out.println("print");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
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

    @Override
    public int seconds(double seconds) {
        return (int) (FRAME_RATE * seconds);
    }

    @Override
    public void execute(Task task) {
        Object lock = new Object();
        synchronized (lock) {
            backGroundTask.append(task.step(() -> {
                synchronized (lock) {
                    lock.notify();
                }
            }));
            try {
                lock.wait(); // Pause execution and wait for the lock to be notified
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public InterruptableTask executeInBackGround(Task task) {
        return backGroundTask.append(task);
    }

    @Override
    public Runnable addBehavior(Runnable task) {
        prePaintTasks.add(task);

        return task;
    }

    @Override
    public <T> Runnable addBehavior(T metadata, Consumer<T> task) {
        return addBehavior(() -> task.accept(metadata));
    }

    @Override
    public void removeBehavior(Runnable r) {
        prePaintTasks.remove(r);
    }

    public void addDragBehavior(Gobject gobject) {
        dragListener.add(gobject);
    }

    public void addClickListener(Gobject gobject, Runnable r) {
        clickListener.add(gobject, r);
    }

    public void addKeyPressedListener(Consumer<KeyEvent> keyEventConsumer) {
        keyListener.addPressedFunction(keyEventConsumer);
    }

    public void addKeyReleasedListener(Consumer<KeyEvent> keyEventConsumer) {
        keyListener.addReleasedFunction(keyEventConsumer);
    }
}
