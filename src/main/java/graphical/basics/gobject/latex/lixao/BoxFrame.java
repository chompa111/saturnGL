package graphical.basics.gobject.latex.lixao;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.latex.GraphicsdrawCharProxy;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;
import org.scilab.forge.jlatexmath.Box;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BoxFrame extends Gobject {

    Box box;

    Location location;

    Color color;

    public BoxFrame(Box box, Location location) {
        this.box = box;
        this.location = location;
    }

    @Override
    public void paint(Graphics g) {
//        ((Graphics2D) g).scale(50,50);
//

        TeXFormula a = new TeXFormula("\\int_{a}^{f(b)+234} x^{(4-1x)} \\,dx");
        var icon2 = a.createTeXIcon(TeXConstants.STYLE_TEXT, 50);


        var img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        var impostor = new GraphicsdrawCharProxy((Graphics2D) img.getGraphics(),null);
        icon2.paintIcon(null, impostor, 500, 400);


        g.setColor(Color.red);
//      //  g.fillOval((int)location.getX(),(int)location.getY(),10,10);
//        box.draw((Graphics2D) g, (float) location.getX(), (float) location.getY());
        Graphics2D g2 = (Graphics2D) g;
        // copy graphics settings
        RenderingHints oldHints = g2.getRenderingHints();
        AffineTransform oldAt = g2.getTransform();
        Color oldColor = g2.getColor();

        // new settings
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.scale(50, 50); // the point size


        // draw formula box
        box.draw(g2, (float) location.getX() / 50, (float) location.getY() / 50);

        // restore graphics settings
        g2.setRenderingHints(oldHints);
        g2.setTransform(oldAt);
        g2.setColor(oldColor);
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return null;
    }

    @Override
    public List<Location> getRefereceLocations() {
        return null;
    }


    public static List<Box> getbaseBoxes(Box actualBox) {
        List<Box> boxes = new ArrayList<>();
        getbaseBoxes(boxes, actualBox);
        return boxes;
    }

    public static void getbaseBoxes(List<Box> boxes, Box actualBox) {
        var children = getChieldren(actualBox);
        if (children == null || children.size() == 0) {
            boxes.add(actualBox);

        } else {
            for (Box b : children) {
                getbaseBoxes(boxes, b);
            }
        }

    }

    public static List<Box> getChieldren(Box box) {
        try {
            var field = Box.class.getDeclaredField("children");
            field.setAccessible(true);
            return (List<Box>) field.get(box);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    void charPositioning(List<BoxFrame> boxFrames, Box box, double x, double y) {
        boxFrames.add(new BoxFrame(box, new Point(x, y)));
    }
}