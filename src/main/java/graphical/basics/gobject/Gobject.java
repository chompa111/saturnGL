package graphical.basics.gobject;


import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.List;


public abstract class Gobject {

    public abstract void paint(Graphics g);

    public abstract LocationPair getBorders();

    public abstract List<ColorHolder> getColors();

    public abstract List<Location> getRefereceLocations();

}
