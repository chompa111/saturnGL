package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class Light extends Gobject {

    Location center;

    NumberHolder radius;

    ColorHolder colorHolder;

    public Light(Location center, NumberHolder radius, ColorHolder colorHolder) {
        this.center = center;
        this.radius = radius;
        this.colorHolder = colorHolder;
    }

    @Override
    public void paint(Graphics g) {
        Point2D center = new Point2D.Float((float) this.center.getX(), (float) this.center.getY());

        float[] dist = {0.0f, 1.0f};
        Color[] colors = {colorHolder.getColor(), new Color(0, 0, 0, 0)};
        RadialGradientPaint p =
                new RadialGradientPaint(center, (float) radius.getValue()/2, dist, colors);

        int r = (int) radius.getValue();
        ((Graphics2D) g).setPaint(p);
        g.fillOval((int) center.getX() - r / 2, (int) center.getY() - r / 2, r, r);

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
        return Arrays.asList(center);
    }
}
