package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Group extends Gobject {

    private List<Gobject> gobjects;


    public Group(Gobject... gobjects) {
        this.gobjects = new ArrayList<>(Arrays.asList(gobjects));
    }

    public Group(List<Gobject> gobjects) {
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
        var borders = new ArrayList<LocationPair>();
        for (Gobject gobject : gobjects) {
            borders.add(gobject.getBorders());
        }

        return new LocationPair(borders);
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


    public ParalelTask onChildren(Function<Gobject, Task> taskFunction, int delay) {
        var list = new ArrayList<Task>();
        for (int i = 0; i < gobjects.size(); i++) {
            list.add(new WaitTask((i * delay) + 1).andThen(taskFunction.apply(gobjects.get(i))));
        }
        return new ParalelTask(list);
    }

    public ParalelTask onChildren(Function<Gobject, Task> taskFunction) {
        var list = new ArrayList<Task>();

        for (Gobject gobject : gobjects) {
            list.add(taskFunction.apply(gobject));
        }

        return new ParalelTask(list);
    }

    public void add(Gobject g) {
        gobjects.add(g);
    }
}
