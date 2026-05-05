package graphical.basics.presentation;

import graphical.basics.gobject.SaturnJComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class AnimationFrame {
    private int offsetX = 0;
    private int offsetY = 0;

    private boolean screenUpdate = true;

    private JFrame frame;

    private RTAnimation animation;

    private List<SaturnJComponent> subComponents = new ArrayList<>();

    public AnimationFrame(RTAnimation animation) {
        this.animation = animation;
        createFrame();
    }

    Rectangle excludedRectangle = new Rectangle(0, 200, 1000, 800);

    void createFrame() {

        RepaintManager.setCurrentManager(new RepaintManager(){
            @Override
            public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
                //super.addDirtyRegion(c,x,y,w,h);
            }
        });
        frame = new JFrame() {
            @Override
            public void paint(Graphics g) {
                var g2d = (Graphics2D) g;

                if (animation.graphicEngine != null)
                    g.drawImage(animation.graphicEngine.getActualFrame(), offsetX, offsetY, null);

                //subComponents.forEach(x->x.getContainer().paint(g));
            }
//
//            @Override
//            public void paintComponents(Graphics g) {
//                //super.paintComponents(g);
//                System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
//            }
//
//            @Override
//            public void paintAll(Graphics g) {
//               // super.paintAll(g);
//                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//            }
//
//            @Override
//            public void repaint() {
//                super.repaint();
//            }
        };

//        Dispatcher dispatcher = new Dispatcher(frame);
//        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
        var presentationConfig = animation.getPresentationConfig();
        //preview windowSize
        frame.setUndecorated(!presentationConfig.isPreviewWindowBarVisible());
        frame.setSize((int) (presentationConfig.getWidth() * presentationConfig.getScale()), (int) (presentationConfig.getHeight() * presentationConfig.getScale()));
        //eable preview
        frame.setLayout(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    void startPaintingCycle() {

        long period = 1000 / animation.getPresentationConfig().getFramerate();

        new Thread(() -> {
            while (true) {
                var before = System.currentTimeMillis();
                animation.processFrame();
                if (screenUpdate) {
                    System.out.println("ping");
                   frame.repaint();
                }
                long delta = System.currentTimeMillis() - before;
                try {
                    System.out.println(delta * 100 / period + "% : delta->"+delta);
                    var sleepTime = period - delta;
                    if (sleepTime > 5) {
                        Thread.sleep(sleepTime);
                    } else {
                        Thread.sleep(5);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setAnimation(RTAnimation animation) {
        this.animation = animation;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void enableScreenUpdate() {
        screenUpdate = true;
    }

    public void disableScreenUpdate() {
        screenUpdate = false;
    }

    public void setOffset(int x, int y) {
        offsetX = x;
        offsetY = y;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void clearFrame(int w, int h, Color c) {
        var g = frame.getGraphics();
        g.setColor(c);
        g.fillRect(0, 0, w, h);
    }

    public void addSaturnJComponent(SaturnJComponent saturnJComponent) {
        subComponents.add(saturnJComponent);
    }
}
