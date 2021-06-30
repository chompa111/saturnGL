package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupGobject extends Gobject {

    private List<Gobject> gobjects;


    public GroupGobject(Gobject... gobjects) {
        this.gobjects = Arrays.asList(gobjects);
    }
    public GroupGobject(List<Gobject> gobjects) {
        this.gobjects = new ArrayList<>(gobjects);
    }

    @Override
    public void paint(Graphics g) {
        for (Gobject gobject : gobjects) {
            gobject.paint(g);
        }
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        var list = new ArrayList<ColorHolder>();
        for (Gobject gobject : gobjects) {
            list.addAll(gobject.getColors());
        }
        return list;
    }

    @Override
    public List<Location> getRefereceLocations() {
        var list = new ArrayList<Location>();
        for (Gobject gobject : gobjects) {
            list.addAll(gobject.getRefereceLocations());
        }
        return list;
    }

    public List<Gobject> getGobjects() {
        return gobjects;
    }
}
