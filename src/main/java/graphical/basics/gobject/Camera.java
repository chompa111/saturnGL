package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Camera extends Gobject {

    private Location location = new Point(500, 500);

    @Override
    public void paint(Graphics g) {
        throw new RuntimeException("the camera object is not a paitable object");
    }

    public void applyView(Graphics g) {
        var g2d = (Graphics2D) g;
        g2d.translate(location.getX(), location.getY());
        g2d.translate(500 - location.getX(), 500 - location.getY());
        g2d.rotate(angle.getValue());
        g2d.scale(scale.getValue(), scale.getValue());
        g2d.translate(-location.getX(), -location.getY());

    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(location, location);
    }

    @Override
    public List<ColorHolder> getColors() {
        throw new RuntimeException("the camera object does not have colors, so the getColors should not be called");
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(location);
    }

}
