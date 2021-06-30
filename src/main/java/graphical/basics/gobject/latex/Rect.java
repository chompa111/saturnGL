package graphical.basics.gobject.latex;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Rect extends Gobject {

    Location p1;
    Location p2;

    ColorHolder colorHolder;

    public Rect(Location p1, Location p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.colorHolder = new ColorHolder(color);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(colorHolder.getColor());
        g.fillRect((int) p1.getX(), (int) p1.getY(), (int) (p2.getX() - p1.getX()), (int) (p2.getY() - p1.getY()));
       // g.fillRect((int) p1.getX(), (int) p1.getY(), (int) (p2.getX()), (int) (p2.getY()));


    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(colorHolder);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(p1, p2);
    }
}
