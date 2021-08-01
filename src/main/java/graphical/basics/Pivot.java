package graphical.basics;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.List;

public class Pivot extends Gobject {
    Gobject gobject;
    Location center;

    public Pivot(Gobject gobject, Location center) {
        this.gobject = gobject;
        this.center = center;
    }

    @Override
    public void paint(Graphics g) {
        gobject.paint(g,false);
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(center,center);
    }

    @Override
    public List<ColorHolder> getColors() {
        return gobject.getColors();
    }

    @Override
    public List<Location> getRefereceLocations() {
        return gobject.getRefereceLocations();
    }
}
