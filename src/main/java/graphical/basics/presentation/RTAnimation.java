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
    public static RTAnimation staticReference;

    public RTAnimation parent;


    private DragListener dragListener;
    private ClickListener clickListener;
    private KeyListener keyListener;

    AnimationFrame animationFrame;

    public static int FRAME_RATE = 60;


    public Graphics bufferedGraphics;

    protected JavaGraphicEngine graphicEngine;

    List<Gobject> gobjects = new ArrayList<>();
    private List<Runnable> prePaintTasks = new ArrayList<>();

    private final BackGround backGround;
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

        this.camera = new Camera(Location.at((presentationConfig.getHeight() / 2.0), presentationConfig.getWidth() / 2.0), presentationConfig.getScale());

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
        if (bufferedGraphics == null) {
            bufferedGraphics = graphicEngine.getGraphics();
        }

    }


    public abstract void buildAnimation();

    public void paintComponent(Graphics g) {
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
        backGroundTask.step();
        dragListener.perform();
        for (int i = 0; i < prePaintTasks.size(); i++) {
            prePaintTasks.get(i).run();
        }
        paintComponent(bufferedGraphics);
    }

    public RTAnimation() {
        staticReference = this;
        AnimationStaticReference.staticReference = this;
        setup(presentationConfig);
        applyConfigs(presentationConfig);
        backGround = new BackGround(presentationConfig.getWidth(), presentationConfig.getHeight());
        animationFrame = new AnimationFrame(this);
        var frame = animationFrame.getFrame();
        dragListener = new DragListener(animationFrame);
        clickListener = new ClickListener(frame);
        keyListener = new KeyListener(frame);
        animationFrame.startPaintingCycle();
    }

    public RTAnimation(RTAnimation rtAnimation) {
        animationFrame=rtAnimation.getAnimationFrame();
        staticReference = this;
        AnimationStaticReference.staticReference = this;
        setup(presentationConfig);
        applyConfigs(presentationConfig);
        backGround = new BackGround(presentationConfig.getWidth(), presentationConfig.getHeight());

        var aframe = rtAnimation.getAnimationFrame();
        var frame = aframe.getFrame();
        dragListener = new DragListener(animationFrame);
        clickListener = new ClickListener(frame);
        keyListener = new KeyListener(frame);
        parent = rtAnimation;
        rtAnimation.getAnimationFrame().setAnimation(this);
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

    @Override
    public void clear() {
        gobjects = new ArrayList<>();
        prePaintTasks = new ArrayList<>();
    }

    public void resetContext() {
        AnimationStaticReference.staticReference = this;
    }

    @Override
    public PresentationConfig getPresentationConfig() {
        return presentationConfig;
    }

    public AnimationFrame getAnimationFrame() {
        return animationFrame;
    }

    public RTAnimation getParent() {
        return parent;
    }

    public void returnParentOwnerShip() {
        AnimationStaticReference.staticReference = parent;
        parent.animationFrame.setAnimation(parent);
    }

    public BackGround getBackGround() {
        return backGround;
    }

    public Graphics getBufferedGraphics() {
        return bufferedGraphics;
    }
}
