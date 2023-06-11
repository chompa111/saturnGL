package graphical.basics.presentation;

import codec.engine.JavaGraphicEngine;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class AnimationFrame {
    private int offsetX = 0;
    private int offsetY = 0;

    private boolean screenUpdate = true;

    private JFrame frame;

    private RTAnimation animation;

    public AnimationFrame(RTAnimation animation) {
        this.animation = animation;
        createFrame();
    }

    void createFrame() {
        frame = new JFrame() {
            @Override
            public void paint(Graphics g) {
                if (animation.graphicEngine != null)
                    g.drawImage(animation.graphicEngine.getActualFrame(), offsetX, offsetY, null);
            }
        };
        var presentationConfig = animation.getPresentationConfig();
        //preview windowSize
        frame.setUndecorated(!presentationConfig.isPreviewWindowBarVisible());
        frame.setSize((int) (presentationConfig.getWidth() * presentationConfig.getScale()), (int) (presentationConfig.getHeight() * presentationConfig.getScale()));
        //eable preview

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    void startPaintingCycle() {
        new Thread(() -> {
            while (true) {
                animation.processFrame();
                if (screenUpdate) {
                    frame.repaint();
                }
                try {
                    Thread.sleep(25);
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

    public void clearFrame(int w, int h, Color c){
        var g=frame.getGraphics();
        g.setColor(c);
        g.fillRect(0,0,w,h);
    }
}
