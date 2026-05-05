package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Group extends Gobject {

    private List<Gobject> gobjects;

    public Group() {
        gobjects = new ArrayList<>();
    }

    public Group(Gobject... gobjects) {
        this.gobjects = new ArrayList<>(Arrays.asList(gobjects));
    }

    public Group(List<Gobject> gobjects) {
        this.gobjects = new ArrayList<>(gobjects);
    }

    @Override
    public void paint(Graphics g) {
        for (Gobject gobject : gobjects) {
            gobject.paint(g, true);
        }
    }

    @Override
    public LocationPair getBorders() {

        if (gobjects.isEmpty()) {
            System.out.println("invalid operation 2");
            return null;
        }

        var borders = new ArrayList<LocationPair>();
        for (Gobject gobject : gobjects) {
            var gobjectBorders = gobject.getBorders();
            if (gobjectBorders != null) {
                borders.add(gobjectBorders);
            }
        }
        if (borders.isEmpty()) {
            return null;
        }

        return new LocationPair(borders, scale.getValue());
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
    public List<Location> getReferenceLocations() {
        var list = new ArrayList<Location>();
        for (Gobject gobject : gobjects) {
            list.addAll(gobject.getReferenceLocations());
        }
        return list;
    }

    public List<Gobject> getGobjects() {
        return gobjects;
    }

    public void setGobjects(List<Gobject> gobjects) {
        this.gobjects = gobjects;
    }

    public ParalelTask onChildren(Function<Gobject, Task> taskFunction, double delay) {
        var list = new ArrayList<Task>();
        for (int i = 0; i < gobjects.size(); i++) {
            list.add(new WaitTask((int) (i * delay) + 1).andThen(taskFunction.apply(gobjects.get(i))));
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

    public void addAll(Collection<Gobject> gobjects) {
        this.gobjects.addAll(gobjects);
    }

    public void addBefore(Gobject referential, Gobject gobject) {
        gobjects.add(gobjects.indexOf(referential), gobject);
    }

    public void add(int index, Gobject g) {
        gobjects.add(index, g);
    }

    public void addAll(List<Gobject> gobjectList) {
        this.gobjects.addAll(gobjectList);
    }

    public void deleteGobjects() {
        this.gobjects = new ArrayList<>();
    }


    public Group subGroup(Integer... index) {
        if (this.gobjects.size() == 0) {
            System.out.println("invalid operation");
            return new Group();
        }
        var list = new ArrayList<Gobject>();
        for (Integer i : index) {
            list.add(gobjects.get(i));
        }
        return new Group(list);
    }
    public Group subGroupInterval(int i, int j) {
        if (this.gobjects.size() == 0) {
            System.out.println("invalid operation");
            return new Group();
        }
        var list = new ArrayList<Gobject>();
        for (int k=i ;k<j;k++) {
            list.add(gobjects.get(k));
        }
        return new Group(list);
    }

    public Group subGroupExept(Integer... index) {
        var list = new ArrayList<Gobject>();
        var set = new HashSet<>(Arrays.asList(index));
        for (int i = 0; i < gobjects.size(); i++) {
            if (!set.contains(i)) {
                list.add(gobjects.get(i));
            }
        }
        return new Group(list);
    }

    public void removeElements(Group group) {
        gobjects.removeAll(group.getGobjects());
    }

    public void remove(Gobject gobject) {
        gobjects.remove(gobject);
    }


    public void remove(int index) {
        gobjects.remove(index);
    }

    @Override
    public Gobject copy() {
        var copy = new Group(gobjects.stream().map(Gobject::copy).collect(Collectors.toList()));
        copyBasicFields(copy, this);
        return copy;
    }

    public Task insertGobjectR(int index, Gobject gobject, double margin) {
        var x = new Integer[this.getGobjects().size()-index];
        for (int i = index ; i < this.getGobjects().size(); i++) {
            x[i-(index)]=i;
        }
        var afterItems=subGroup(x);

        var curObj=subGroup(index);
        var prevObj=subGroup(index-1);

        var space = curObj.getBorders().getL1().getX()-prevObj.getBorders().getL2().getX();
        var gobjectWidth=gobject.getWidth()+margin;


        var delta = -(space-gobjectWidth);
        var finalDest=prevObj.getMidPoint().plus(delta/2+(prevObj.getWidth()/2),0);

        return afterItems.move(delta,0).parallel(gobject.moveTo(finalDest));
    }
}
