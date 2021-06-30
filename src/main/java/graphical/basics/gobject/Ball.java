package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Ball extends Gobject {

    Location location;
    double velx;
    double vely;
    ColorHolder colorHolder;

    List<ColorHolder> colorHolders;
    List<Location> locations;

    @Override
    public void paint(Graphics g) {
        g.setColor(colorHolder.getColor());
        g.fillOval((int) location.getX(), (int) location.getY(), 40, 40);
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return colorHolders;
    }

    @Override
    public List<Location> getRefereceLocations() {
        return locations;
    }

    public void update() {
        location.setX(location.getX() + velx);
        location.setY(location.getY() + vely);


        if (location.getX() > 1000) {
            location.setX(1000);
            velx = -velx;
        }

        if (location.getX() < 0) {
            location.setX(0);
            velx = -velx;
        }

        if (location.getY() > 1000) {
            location.setY(1000);
            vely = -vely;
        }

        if (location.getY() < 0) {
            location.setY(0);
            vely = -vely;
        }
    }

    public Ball(Location location, double velx, double vely) {
        this.location = location;
        this.locations = Arrays.asList(location);
        this.velx = velx;
        this.vely = vely;
        this.colorHolder = new ColorHolder(new Color(255, 0, 255, (int) (Math.random() * 255)));
        this.colorHolders = Arrays.asList(colorHolder);
    }

    public Ball(Location location, double velx, double vely, Color c) {
        this.location = location;
        this.locations = Arrays.asList(location);
        this.velx = velx;
        this.vely = vely;
        this.colorHolder = new ColorHolder(c);
        this.colorHolders = Arrays.asList(colorHolder);
    }

    public Ball(Location location, Color c) {
        this.location = location;
        this.locations = Arrays.asList(location);
        this.colorHolder = new ColorHolder(c);
        this.colorHolders = Arrays.asList(colorHolder);
    }
}
