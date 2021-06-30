package codec;

import graphical.basics.gobject.Ball;
import graphical.basics.location.Point;
import graphical.basics.location.SupplierPoint;
import graphical.basics.task.transformation.gobject.ColorTranform;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PresentationEx extends JFrame {

    BufferedImage bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
    Graphics bufferedGraphics = bufferedImage.getGraphics();

    VideoCodec videoCodec = new JavcCodec();
    List<Ball> balls = new ArrayList<>();

    ColorTranform colorTranform;




    public PresentationEx() {

        var p1 = new Point(500, 500);
        var p2 = new SupplierPoint(0, 900, p1::getX, () -> 0.0);
        var p3 = new SupplierPoint(100, 0, () -> 0.0, p1::getY);
        var b1 = new Ball(p1, (Math.random() * 20) - 10, (Math.random() * 20) - 10, Color.yellow);
        var b2 = new Ball(p2, 0, 0, Color.CYAN);
        var b3 = new Ball(p3, 0, 0, Color.CYAN);

        balls.add(b1);
        balls.add(b2);
        balls.add(b3);

        colorTranform = new ColorTranform(b1, Color.MAGENTA, 100);

        videoCodec.startNewVideo("", "pepe.mov", 30);
//        videoCodec.addFrame(null);
//        videoCodec.saveVideo();

        Graphics2D g2 = (Graphics2D) bufferedGraphics;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        setSize(1000, 1000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

//        new Thread(()->{
//            while (true){
//                repaint();
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, 0, null);
    }

    public void frame() throws InterruptedException {
        var before1 = System.currentTimeMillis();
        paintComponent(bufferedGraphics);
        repaint();
        System.out.println((System.currentTimeMillis() - before1) + "ms processando quadro");
        var before2 = System.currentTimeMillis();
        videoCodec.addFrame(bufferedImage);
        System.out.println((System.currentTimeMillis() - before1) + "ms de codec");
        for (Ball ball : balls) ball.update();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 1000);

        for (Ball ball : balls) ball.paint(g);
    }

    public static void main(String[] args) throws InterruptedException {
        var presentation = new PresentationEx();
        while (!presentation.colorTranform.isDone()){
            presentation.colorTranform.step();
            presentation.frame();
        }


        presentation.videoCodec.saveVideo();
    }
}
