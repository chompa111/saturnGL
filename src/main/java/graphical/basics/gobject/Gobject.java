package graphical.basics.gobject;


import graphical.basics.presentation.Presentation;
import graphical.basics.AnimationBuilder;
import graphical.basics.ColorHolder;
import graphical.basics.behavior.Behavior;
import graphical.basics.behavior.FollowBehavior;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.SupplierPoint;
import graphical.basics.task.Task;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.MorfTransform;
import graphical.basics.task.transformation.gobject.PositionTransform;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;


public abstract class Gobject implements AnimationBuilder {

    NumberHolder ang;
    public NumberHolder zoom = new DoubleHolder(1);

    public void paint(Graphics g, boolean b) {
        var midpoint = getBorders().midPoint();
        var g2d = (Graphics2D) g;
        var oldT = (AffineTransform) g2d.getTransform().clone();
        g.translate((int) midpoint.getX(), (int) midpoint.getY());
        g2d.scale(zoom.getValue(), zoom.getValue());
        g.translate(-(int) midpoint.getX(), -(int) midpoint.getY());
        paint(g);

        g2d.setTransform(oldT);
    }

    public abstract void paint(Graphics g);

    public abstract LocationPair getBorders();

    public abstract List<ColorHolder> getColors();

    public abstract List<Location> getRefereceLocations();


    //

    public Task transform(Gobject gobject2, int steps) {
        return new MorfTransform(this, gobject2, steps);
    }

    public Task transform(Gobject gobject2) {
        return transform(gobject2, Presentation.staticReference.seconds(1));
    }


    public Task changeColor(Color color, int steps) {
        return new ColorTranform(this, color, steps);
    }

    public Task changeColor(Color color) {
        return changeColor(color, Presentation.staticReference.seconds(1));
    }


    public Task move(double x, double y, int steps) {
        return new PositionTransform(this, x, y, steps);
    }

    public Task move(double x, double y) {
        return move(x, y, Presentation.staticReference.seconds(1));
    }

    public void changeSetPosition(double x, double y) {
        for (Location location : this.getRefereceLocations()) {
            location.incrementX(x);
            location.incrementY(y);
        }
    }

    public Behavior asSubtitle(Gobject gobject,int border) {
        var myBorders = this.getBorders();
        var subBorders = gobject.getBorders();

        var mid1 = myBorders.midPoint();
        var mid2 = subBorders.midPoint();

        var deltaX = mid1.getX() - mid2.getX();
        var deltaY = myBorders.getL1().getY() - subBorders.getL1().getY()+(myBorders.getL2().getY()-myBorders.getL1().getY()+border);

        gobject.changeSetPosition(deltaX, deltaY);

        return new FollowBehavior(gobject, new SupplierPoint(()->this.getBorders().midPoint().getX(),()->this.getBorders().getL2().getY()));

    }

}
