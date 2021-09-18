package graphical.basics.gobject.latex;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.FillAndStroke;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

public class Rect extends FillAndStroke implements ShapeLike {

    Location p1;
    Location p2;


    public Rect(Location p1, Location p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.fillColorHolder = new ColorHolder(color);
    }

    @Override
    public void paint(Graphics g) {

        if(fillColorHolder!=null){
            g.setColor(fillColorHolder.getColor());
            g.fillRect((int) p1.getX(), (int) p1.getY(), (int) (p2.getX() - p1.getX()), (int) (p2.getY() - p1.getY()));
        }

        if (strokeColorHolder != null) {
            ((Graphics2D)g).setStroke(new BasicStroke((float)getStrokeThickness().getValue()));
            g.setColor(strokeColorHolder.getColor());
            g.drawRect((int) p1.getX(), (int) p1.getY(), (int) (p2.getX() - p1.getX()), (int) (p2.getY() - p1.getY()));
        }

    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(p1, p2);
    }


    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(p1, p2);
    }

    @Override
    public Shape asShape() {
        var rect = new Rectangle((int) p1.getX(), (int) p1.getY(), (int) (p2.getX() - p1.getX()), (int) (p2.getY() - p1.getY()));

        return this.getTranformation().createTransformedShape(rect);
    }

    public Location getP1() {
        return p1;
    }

    public Location getP2() {
        return p2;
    }
}
