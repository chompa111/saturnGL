package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Arc extends Gobject {

    Location center;
    NumberHolder radius;
    NumberHolder initAng;
    NumberHolder ang;
    ColorHolder colorHolder;


    public Arc(Location center, NumberHolder radius, NumberHolder initAng, NumberHolder ang, Color color) {
        this.center = center;
        this.radius = radius;
        this.initAng = initAng;
        this.ang = ang;
        this.colorHolder = new ColorHolder(color);
    }

    public Arc(Location center, NumberHolder radius, NumberHolder ang, Color color) {
        this.center = center;
        this.radius = radius;
        this.initAng = new DoubleHolder(0);
        this.ang = ang;
        this.colorHolder = new ColorHolder(color);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(colorHolder.getColor());
        g.drawArc((int) (center.getX() - radius.getValue() / 2), (int) (center.getY() - radius.getValue() / 2), (int) radius.getValue(), (int) radius.getValue(), (int) initAng.getValue(), (int) ang.getValue());
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(new graphical.basics.location.Point(center.getX() - radius.getValue() / 2, center.getY() - radius.getValue() / 2),
                new Point(center.getX() + radius.getValue() / 2, center.getY() + radius.getValue() / 2));
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(colorHolder);
    }

    @Override
    public List<Location> getReferenceLocations() {
        return Arrays.asList(center);
    }
}
