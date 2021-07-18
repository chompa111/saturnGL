package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ShapeGobject extends Gobject {
    ColorHolder colorHolderFill;
    ColorHolder colorHolderStroke;
    Shape shape;
    Location location;


    public ShapeGobject() {
    }

    public ShapeGobject(Shape shape, String style) {
        for (String stylePart : style.split(";")) {
            var value = stylePart.split(":")[1];
            switch (stylePart.split(":")[0]) {
                case "fill":
                    if (!value.equals("none"))
                        colorHolderFill = new ColorHolder(ColorHolder.hex2Rgb(stylePart.split(":")[1]));
                    break;
                case "stroke":
                    if (!value.equals("none"))
                        colorHolderStroke = new ColorHolder(ColorHolder.hex2Rgb(stylePart.split(":")[1]));
                    break;

            }

        }
        var bounds = shape.getBounds();
        // location=new Point((2.0*bounds.getX()+bounds.getWidth())/2,(2.0*bounds.getY()+bounds.getHeight())/2);
        location = new Point(0, 0);
        this.shape = shape;
    }

    @Override
    public void paint(Graphics g) {
        var g2d = ((Graphics2D) g);
//
//        Stroke s = new BasicStroke(1.0f,                      // Width
//                BasicStroke.CAP_SQUARE,    // End cap
//                BasicStroke.JOIN_MITER,    // Join style
//                10.0f,                     // Miter limit
//                new float[] {50,1000}, // Dash pattern
//                0.0f);
//        ((Graphics2D) g).setStroke(s);

        ((Graphics2D) g).setStroke(new BasicStroke(1));

        var transf = g2d.getTransform();
        g2d.translate(location.getX(), location.getY());
        if (colorHolderFill != null) {
            g.setColor(colorHolderFill.getColor());
            g2d.fill(shape);
        }
        if (colorHolderStroke != null) {
            g.setColor(colorHolderStroke.getColor());
            g2d.draw(shape);
        }

        g2d.setTransform(transf);

    }

    @Override
    public LocationPair getBorders() {

        var bounds = shape.getBounds();
        double x = bounds.getBounds().getX() + location.getX();
        double y = bounds.getY() + location.getY();
        return new LocationPair(new Point(x, y), new Point(x + bounds.getWidth(), x + bounds.getHeight()));
    }

    @Override
    public List<ColorHolder> getColors() {
        var ordefaultFill = colorHolderFill != null ? colorHolderFill : new ColorHolder(Color.white);
        var orDeafaultStroke = colorHolderStroke != null ? colorHolderStroke : new ColorHolder(Color.white);
        return Arrays.asList(ordefaultFill, orDeafaultStroke);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(location);
    }

    public Shape getShape() {
        return shape;
    }

    public ColorHolder getColorHolderFill() {
        return colorHolderFill;
    }

    public ColorHolder getColorHolderStroke() {
        return colorHolderStroke;
    }
}
