package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.FillAndStroke;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.List;

public class Line extends FillAndStroke implements ShapeLike {

    Location p1;
    Location p2;
    ColorHolder colorHolder;

    Line2D.Double line2D;

    public Line(Location p1, Location p2, Color color, NumberHolder thickness) {
        super(color, color);
        this.p1 = p1;
        this.p2 = p2;
        this.line2D = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        this.colorHolder = new ColorHolder(color);
        setStrokeThickness(thickness);
    }

    public Line(Location p1, Location p2, Color color) {
        super(color, color);
        this.p1 = p1;
        this.p2 = p2;
        this.line2D = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        this.colorHolder = new ColorHolder(color);
    }


    @Override
    public void paint(Graphics g) {

        line2D.setLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke((float) getStrokeThickness().getValue()));
        g.setColor(colorHolder.getColor());
        g2.draw(line2D);
        g2.setStroke(new BasicStroke(1));

    }

    public void paint(Graphics g, double perc) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke((float) getStrokeThickness().getValue()));
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
    public List<Location> getReferenceLocations() {
        return Arrays.asList(p1, p2);
    }


    public double lenght() {
        return Math.sqrt(((p1.getX() - p2.getX()) * (p1.getX() - p2.getX())) + ((p1.getY() - p2.getY()) * (p1.getY() - p2.getY())));
    }

    @Override
    public Shape asShape() {
        return new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public Location getP1() {
        return p1;
    }

    public Location getP2() {
        return p2;
    }

    @Override
    public Gobject copy() {
        var copy = new Line(p1.copy(), p2.copy(), colorHolder.getColor());
        copyBasicFields(copy, this);
        return copy;
    }
}
