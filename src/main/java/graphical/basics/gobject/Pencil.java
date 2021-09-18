package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.Arrays;
import java.util.List;

public class Pencil extends Gobject implements ShapeLike {

    Location previusLocation;
    Location location;
    Color color;
    GeneralPath generalPath= new GeneralPath();

    public Pencil(Location location, Color color) {
        this.location = location;
        this.previusLocation=location.copy();
        this.color = color;
        generalPath.moveTo(location.getX(),location.getY());
    }


    @Override
    public Shape asShape() {
        return generalPath;
    }

    @Override
    public void paint(Graphics g) {
        if (!previusLocation.equals(location)) {
            generalPath.lineTo(location.getX(),location.getY());
            previusLocation.setX(location.getX());
            previusLocation.setY(location.getY());
        }
        g.setColor(color);
        ((Graphics2D)g).draw(generalPath);
    }

    @Override
    public LocationPair getBorders() {
        var bounds = generalPath.getBounds();
        double x = bounds.getBounds().getX();
        double y = bounds.getY() + location.getY();
        return new LocationPair(new graphical.basics.location.Point(x, y), new Point(x + bounds.getWidth(), y + bounds.getHeight()));
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(new ColorHolder(color));
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(Location.at(500,500));
    }
}
