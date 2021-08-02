package graphical.basics.gobject.struct;


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

import static graphical.basics.presentation.Positioning.Reference.BOTTOM;


public abstract class Gobject {

    NumberHolder angle = new DoubleHolder(0);
    public NumberHolder scale = new DoubleHolder(1);

    public void paint(Graphics g, boolean b) {


        var midpoint = getBorders().midPoint();
        var g2d = (Graphics2D) g;
        var oldT = (AffineTransform) g2d.getTransform().clone();
        g2d.translate(midpoint.getX(), midpoint.getY());
        g2d.rotate(angle.getValue());
        g2d.scale(scale.getValue(), scale.getValue());
        g2d.translate(-midpoint.getX(), -midpoint.getY());
        paint(g);

        g2d.setTransform(oldT);

        var x = getBorders().midPoint();
        g.setColor(Color.green);
        var xx = (int) x.getX();
        var yy = (int) x.getY();
        g.setFont(new Font("Consola", Font.BOLD, 8));
        g.drawString(xx + "," + yy, xx + 3, yy);
        g.fillOval(xx - 3, yy - 3, 6, 6);
        g.setColor(Color.black);
        g.fillOval(xx - 1, yy - 1, 2, 2);
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

    public void changeSetPosition(double x, double y) {
        for (Location location : this.getRefereceLocations()) {
            location.incrementX(x);
            location.incrementY(y);
        }
    }

    public Behavior asSubtitle(Gobject gobject, Positioning.Reference reference) {
        //por enquanto
        switch (reference) {
            case BOTTOM:
                Positioning.align(gobject, this, BOTTOM);
        }

        return new FollowBehavior(gobject, new SupplierPoint(() -> this.getBorders().midPoint().getX(), () -> this.getBorders().getL2().getY()));

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

}
