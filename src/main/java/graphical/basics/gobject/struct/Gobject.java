package graphical.basics.gobject.struct;


import graphical.basics.Pivot;
import graphical.basics.presentation.Effects;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
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


public abstract class Gobject {

    NumberHolder angle = new DoubleHolder(0);
    public NumberHolder scale = new DoubleHolder(1);

    public void paint(Graphics g, boolean b) {


        var midpoint = getBorders().midPoint();
        var g2d = (Graphics2D) g;
        var oldT = (AffineTransform) g2d.getTransform().clone();
        g.translate((int) midpoint.getX(), (int) midpoint.getY());
        g2d.scale(scale.getValue(), scale.getValue());
        g2d.rotate(angle.getValue());
        g.translate(-(int) midpoint.getX(), -(int) midpoint.getY());
        paint(g);

        g2d.setTransform(oldT);

//        var x = getBorders().midPoint();
//        g.setColor(Color.green);
//        var xx = (int) x.getX();
//        var yy = (int) x.getY();
//        g.setFont(new Font("Consola", Font.BOLD, 8));
//        g.drawString(xx + "," + yy, xx + 3, yy);
//        g.fillOval(xx - 3, yy - 3, 6, 6);
//        g.setColor(Color.black);
//        g.fillOval(xx - 1, yy - 1, 2, 2);
    }

    public abstract void paint(Graphics g);

    public abstract LocationPair getBorders();

    public abstract List<ColorHolder> getColors();

    public abstract List<Location> getRefereceLocations();

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

    public Task init() {
        return Effects.init(this);
    }

    public void changeSetPosition(double x, double y) {
        for (Location location : this.getRefereceLocations()) {
            location.incrementX(x);
            location.incrementY(y);
        }
    }

    public Behavior asSubtitle(Gobject gobject, int border) {
        var myBorders = this.getBorders();
        var subBorders = gobject.getBorders();

        var mid1 = myBorders.midPoint();
        var mid2 = subBorders.midPoint();

        var deltaX = mid1.getX() - mid2.getX();
        var deltaY = myBorders.getL1().getY() - subBorders.getL1().getY() + (myBorders.getL2().getY() - myBorders.getL1().getY() + border);

        gobject.changeSetPosition(deltaX, deltaY);

        return new FollowBehavior(gobject, new SupplierPoint(() -> this.getBorders().midPoint().getX(), () -> this.getBorders().getL2().getY()));

    }

    public Behavior asSubtitle(Gobject gobject, Positioning.Reference reference) {
        switch (reference) {
            case BOTTOM:
                Positioning.alignX(gobject, Positioning.Reference.CENTER, this, Positioning.Reference.CENTER);
                Positioning.alignY(gobject, Positioning.Reference.TOP, this, Positioning.Reference.BOTTOM);
                break;
            case RIGHT:
                Positioning.alignX(gobject, Positioning.Reference.LEFT, this, Positioning.Reference.RIGHT);
                Positioning.alignY(gobject, Positioning.Reference.CENTER, this, Positioning.Reference.CENTER);
                break;
            case LEFT:
                Positioning.alignX(gobject, Positioning.Reference.RIGHT, this, Positioning.Reference.LEFT);
                Positioning.alignY(gobject, Positioning.Reference.CENTER, this, Positioning.Reference.CENTER);
                break;
            case TOP:
                Positioning.alignX(gobject, Positioning.Reference.CENTER, this, Positioning.Reference.CENTER);
                Positioning.alignY(gobject, Positioning.Reference.BOTTOM, this, Positioning.Reference.TOP);
                break;
            case CENTER:
                Positioning.alignX(gobject, Positioning.Reference.CENTER, this, Positioning.Reference.CENTER);
                Positioning.alignY(gobject, Positioning.Reference.CENTER, this, Positioning.Reference.CENTER);
        }
        return null;
    }


    public NumberHolder getScale() {
        return scale;
    }

    public NumberHolder getAngle() {
        return angle;
    }

    public Task scale(double factor, int steps) {
        var actual = scale.getValue();
        return scale.change(actual * factor - actual, steps);
    }

    public Task scale(double amount) {
        return scale(amount, Presentation.staticReference.seconds(1));
    }

    public Task rotate(double amount, int steps) {
        return angle.change(amount, steps);
    }

    public Task rotate(double amount) {
        return angle.change(amount);
    }

    public Pivot createPivot(Location location) {
        var x = new Pivot(this, location);


        return x;
    }

}
