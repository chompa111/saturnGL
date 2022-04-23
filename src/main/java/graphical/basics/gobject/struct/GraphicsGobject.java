package graphical.basics.gobject.struct;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GraphicsGobject extends Gobject{

    Location center;

    public GraphicsGobject(Location center) {
        this.center = center;
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(center,center);
    }

    @Override
    public List<ColorHolder> getColors() {
        return new ArrayList<>();
    }

    @Override
    public List<Location> getRefereceLocations() {
        return List.of(center);
    }
}
