package graphical.basics.miscelaneous;

import graphical.basics.gobject.AwtGobject;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.Rect;
import graphical.basics.gobject.struct.ClipArea;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class ExCode2 extends Baskara {
//    @Override
//    public void setup(PresentationConfig presentationConfig) {
//        //  presentationConfig.setDisableCodec(true);
//        presentationConfig.setFramerate(15);
//    }

    @Override
    public void buildAnimation() {
        var srcImg = new BufferedImage(this.getPresentationConfig().getWidth(), this.getPresentationConfig().getHeight(), TYPE_INT_RGB);
        var srcGraphics = srcImg.getGraphics();

        var finalImg = new BufferedImage(this.getPresentationConfig().getWidth(), this.getPresentationConfig().getHeight(), TYPE_INT_RGB);
        var finalGraphics = finalImg.getGraphics();

        final var ref = new Object() {
            Gobject ex = null;
        };

        var rect = new Rect(Location.at(0, 0), Location.at(200, 200), Color.white);
        var clip = new ClipArea(rect);

        ref.ex = new AwtGobject((g) -> {
            if (getObjectIndex(clip) != getGobjects().size() - 1) {
                remove(clip);
                add(clip);
            }

            var allGobjects = new Group(getGobjects());
            allGobjects.remove(clip);
            srcGraphics.clearRect(0, 0, 1000, 1000);
            finalGraphics.clearRect(0, 0, 1000, 1000);
            allGobjects.paint(srcGraphics);


//                int size = 5;
//                for (int i = 0; i < 1000; i += size) {
//                    for (int j = 0; j < 1000; j += size) {
//                        var color = getMeanColorOfCircle(i, j, size, srcImg);
//                        finalGraphics.setColor(color);
//                        finalGraphics.fillOval(i - size / 2, j - size / 2, size, size);
//                    }
//                }
//
//            for (int i = 0; i < 100000; i++) {
//                int x = (int) (Math.random() * 1000);
//                int y = (int) (Math.random() * 1000);
//                int radius = (int) (2 + Math.random() * 6);
//                var color = getMeanColorOfCircle(x, y, radius, srcImg);
//                finalGraphics.setColor(color);
//                finalGraphics.fillOval(x - radius / 2, y - radius / 2, radius, radius);
//            }
            int size = 4;
            for (int i = 0; i < 1000; i += size) {
                for (int j = 0; j < 1000; j += size) {
                    try {
                        var color = getMeanColorOfSquare(i, j, size, srcImg);
                        finalGraphics.setColor(color);
                        finalGraphics.fillRect(i, j, size, size);
                    } catch (Exception e) {
                        System.out.println("s");
                    }
                }
            }
            ((Graphics2D) finalGraphics).setStroke(new BasicStroke(size));
            finalGraphics.setColor(new Color(255, 255, 255, 30));
            //  finalGraphics.setColor(Color.red);
            for (int i = 0; i < 1000; i += size) {
                if (i % (2 * size) == 0) {
                    finalGraphics.drawLine(0, i, 1000, i);
                }

            }

            g.drawImage(finalImg, 0, 0, null);
        });
        clip.add(ref.ex);
        add(clip);

        rect.move(350,0,seconds(3))
                .andThen(rect.move(-350,0,seconds(3)))
                .repeat(10).executeInBackGround();

//        var circle = CircleBuilder.aCircle().build();
//        add(circle);
//
//        var mainFormula = new TextGobject("x^2+2x+1=0", new Point(500, 500), Color.white);
//        //add(mainFormula);
//
//
//        add(ref.ex);
//
//        //wait(1).andThen(pixel.change(10,seconds(1))).executeInBackGround();
//        circle.move(300, 0, seconds(3)).
//                parallel(circle.changeColor(Color.magenta, seconds(3))).execute();
//        wait(seconds(2)).execute();
//
//        circle.transform(mainFormula, seconds(2)).execute();
//
//        mainFormula.subGroup(0, 1).changeColor(Color.ORANGE, seconds(2)).execute();
//        Animation.strokeAndFill(mainFormula, seconds(2)).execute();
        super.buildAnimation();
    }

    boolean isInsideCircle(int cx, int cy, double radius, int x, int y) {
        return Math.sqrt(Math.pow(cx - x, 2) + Math.pow(cy - y, 2)) <= radius;
    }

    Color getMeanColorOfSquare(int x, int y, double size, BufferedImage image) {
        double red = 0, green = 0, blue = 0;
        int count = 0;
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                //   System.out.println("sada");
                if (i < 0 || i >= 1000 || j < 0 || j >= 1000) continue;

                var color = image.getRGB(i, j);
                blue += color & 0xff;
                green += (color & 0xff00) >> 8;
                red += (color & 0xff0000) >> 16;
                count++;

            }
        }
        if (red > 0) {
            System.out.println();
        }

        return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
    }


    Color getMeanColorOfCircle(int cx, int cy, double radius, BufferedImage image) {
        double red = 0, green = 0, blue = 0;
        int count = 0;
        for (int x = (int) (cx - radius); x < cx + radius; x++) {
            for (int y = (int) (cy - radius); y < cy + radius; y++) {
                if (x < 0 || x >= 1000 || y < 0 || y >= 1000) continue;
                if (isInsideCircle(cx, cy, radius, x, y)) {
                    var color = image.getRGB(x, y);
                    blue += color & 0xff;
                    green += (color & 0xff00) >> 8;
                    red += (color & 0xff0000) >> 16;
                    count++;
                }
            }
        }

        var select = (cx / 5) % 3;
        switch (select) {
            case 0:
                return new Color((int) (red / count), (int) (green * 0.8 / count), (int) (blue * 0.8 / count), 200);
            case 1:
                return new Color((int) (red * 0.8 / count), (int) (green / count), (int) (blue * 0.8 / count), 200);
            case 2:
                return new Color((int) (red * 0.8 / count), (int) (green * 0.8 / count), (int) (blue / count), 200);
            default:
                return new Color((int) (red / count), (int) (green / count), (int) (blue / count), 200);

        }

    }

    public static void main(String[] args) {
        new ExCode2().build();
    }

}
