package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class GradientBox extends Gobject {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;


    Location p1;
    Location p2;

    ColorHolder color1;
    ColorHolder color2;

    int mode;


    public GradientBox(Location p1, Location p2, Color color1, Color color2, int mode) {
        this.p1 = p1;
        this.p2 = p2;
        this.color1 = new ColorHolder(color1);
        this.color2 = new ColorHolder(color2);
        this.mode = mode;
    }

    @Override
    public void paint(Graphics g) {
        var paint = ((Graphics2D) g).getPaint();
        switch (mode) {
            case HORIZONTAL:
                ((Graphics2D) g).setPaint(new GradientPaint(new Point2D.Double(p1.getX(), p1.getY()), color1.getColor(), new Point2D.Double(p2.getX(), p1.getY()), color2.getColor()));
                break;
            case VERTICAL:
                ((Graphics2D) g).setPaint(new GradientPaint(new Point2D.Double(p1.getX(), p1.getY()), color1.getColor(), new Point2D.Double(p1.getX(), p2.getY()), color2.getColor()));
                break;
        }

        g.fillRect((int) p1.getX(), (int) p1.getY(), (int) (p2.getX() - p1.getX()), (int) (p2.getY() - p1.getY()));
        ((Graphics2D) g).setPaint(paint);
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(p1, p2);
    }

    @Override
    public List<ColorHolder> getColors() {
        return List.of(color1, color2);
    }

    @Override
    public List<Location> getReferenceLocations() {
        return Arrays.asList(p1, p2);
    }

    public Location getP1() {
        return p1;
    }

    public Location getP2() {
        return p2;
    }
}
