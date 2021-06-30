package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Circle extends Gobject {

    Location center;
    NumberHolder radio;
    ColorHolder colorHolder;

    public Circle(Location center, NumberHolder radio, Color color) {
        this.center = center;
        this.radio = radio;
        this.colorHolder = new ColorHolder(color);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(colorHolder.getColor());

        int r = (int) radio.getValue();
        g.fillOval((int) center.getX() - r / 2, (int) center.getY() - r / 2, r, r);
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(new Point(center.getX() - radio.getValue(), center.getY() - radio.getValue()),
                new Point(center.getX() + radio.getValue(), center.getY() + radio.getValue()));
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(colorHolder);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(center);
    }

    public NumberHolder getRadio() {
        return radio;
    }

    public void setRadio(NumberHolder radio) {
        this.radio = radio;
    }

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public ColorHolder getColorHolder() {
        return colorHolder;
    }

    public void setColorHolder(ColorHolder colorHolder) {
        this.colorHolder = colorHolder;
    }
}
