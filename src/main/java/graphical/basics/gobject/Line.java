package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.List;

public class Line extends Gobject implements ShapeLike {

    Location p1;
    Location p2;
    ColorHolder colorHolder;
    NumberHolder thickness;

    public Line(Location p1, Location p2, Color color, NumberHolder thickness) {
        this.p1 = p1;
        this.p2 = p2;
        this.colorHolder = new ColorHolder(color);
        this.thickness = thickness;
    }

    public Line(Location p1, Location p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.colorHolder = new ColorHolder(color);
        this.thickness = new DoubleHolder(1);
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke((float) thickness.getValue()));
        g.setColor(colorHolder.getColor());
        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        g2.setStroke(new BasicStroke(1));

    }

    public void paint(Graphics g, double perc) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke((float) thickness.getValue()));
        g.setColor(colorHolder.getColor());
        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) (p1.getX() + (p2.getX() - p1.getX()) * perc), (int) (p1.getY() + (p2.getY() - p1.getY()) * perc));
        g2.setStroke(new BasicStroke(1));

    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(p1, p2);
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(colorHolder);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(p1, p2);
    }


    public double lenght() {
        return Math.sqrt(((p1.getX() - p2.getX()) * (p1.getX() - p2.getX())) + ((p1.getY() - p2.getY()) * (p1.getY() - p2.getY())));
    }

    @Override
    public Shape asShape() {
        return new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
}
