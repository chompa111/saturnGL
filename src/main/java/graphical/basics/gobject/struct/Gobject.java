package graphical.basics.gobject.struct;


import graphical.basics.ColorHolder;
import graphical.basics.gobject.Group;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.presentation.AnimationStaticReference;
import graphical.basics.task.TimeDefinedTask;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.MorfTransform;
import graphical.basics.task.transformation.gobject.PositionTransform;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


public abstract class Gobject {

    public NumberHolder angle = new DoubleHolder(0);
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

    public abstract List<Location> getReferenceLocations();

    public TimeDefinedTask transform(Gobject gobject2, int steps) {
        return new MorfTransform(this, gobject2, steps);
    }

    public TimeDefinedTask transform(Gobject gobject2) {
        return transform(gobject2, AnimationStaticReference.staticReference.seconds(1));
    }


    public TimeDefinedTask changeColor(Color color, int steps) {
        return new ColorTranform(this, color, steps);
    }

    public void setColor(Color color) {
        for (ColorHolder ch : this.getColors()) {
            ch.setColor(color);
        }
    }

    public TimeDefinedTask changeColor(Color color) {
        return changeColor(color, AnimationStaticReference.staticReference.seconds(1));
    }


    public TimeDefinedTask move(double x, double y, int steps) {
        return new PositionTransform(this, x, y, steps);
    }

    public TimeDefinedTask move(double x, double y) {
        return move(x, y, AnimationStaticReference.staticReference.seconds(1));
    }

    public TimeDefinedTask moveTo(Location location, int steps) {
        var mylocation = getBorders().midPoint();
        var diffx = location.getX() - mylocation.getX();
        var diffy = location.getY() - mylocation.getY();

        return this.move(diffx, diffy, steps);
    }

    public TimeDefinedTask moveTo(Location location) {
        return this.moveTo(location, AnimationStaticReference.staticReference.seconds(1));
    }


    public void changeSetPosition(double x, double y) {
        for (Location location : this.getReferenceLocations()) {
            location.incrementX(x);
            location.incrementY(y);
        }
    }

    public void setPositionTo(Location location) {
        var myLocation = this.getBorders().midPoint();
        var diffx = location.getX() - myLocation.getX();
        var diffy = location.getY() - myLocation.getY();
        changeSetPosition(diffx, diffy);
    }

    public AffineTransform getTranformation() {

        var midpoint = getBorders().midPoint();
        var af = new AffineTransform();

        af.translate(midpoint.getX(), midpoint.getY());
        af.rotate(angle.getValue());
        af.scale(scale.getValue(), scale.getValue());
        af.translate(-midpoint.getX(), -midpoint.getY());

        return af;
    }


    public NumberHolder getScale() {
        return scale;
    }

    public NumberHolder getAngle() {
        return angle;
    }

    public TimeDefinedTask scale(double factor, int steps) {
        var actual = scale.getValue();
        return scale.change(actual * factor - actual, steps);
    }

    public TimeDefinedTask scale(double amount) {
        return scale(amount, AnimationStaticReference.staticReference.seconds(1));
    }

    public TimeDefinedTask rotate(double amount, int steps) {
        return angle.change(amount, steps);
    }

    public TimeDefinedTask rotate(double amount) {
        return angle.change(amount);
    }


    public Location getMidPoint() {
        return getBorders().midPoint();
    }

    public Location getBellowPoint() {
        var border = getBorders();
        return border.midPoint().plus(0, border.getheight() / 2);
    }

    public Location getRightPoint() {
        var border = getBorders();
        return border.midPoint().plus(border.getwidth() / 2, 0);
    }

    public Location getLeftPoint() {
        var border = getBorders();
        return border.midPoint().plus(-border.getwidth() / 2, 0);
    }

    public Location getOverPoint() {
        var border = getBorders();
        return border.midPoint().plus(0, -border.getheight() / 2);
    }

    public double getWidth() {
        return getBorders().getwidth();
    }

    public double getHeight() {
        return getBorders().getheight();
    }

    public Gobject copy() {
        throw new RuntimeException("not implemented yet");
    }

    protected void copyBasicFields(Gobject copy, Gobject source) {
        copy.getScale().setValue(source.getScale().getValue());
        copy.getAngle().setValue(source.getAngle().getValue());
    }

    public Group and(Gobject... gobjects) {
        var result = new ArrayList<Gobject>();
        result.add(this);
        result.addAll(Arrays.asList(gobjects));
        return new Group(result);
    }

    public Runnable addBehavior(Runnable task) {
        return AnimationStaticReference.staticReference.addBehavior(task);
    }

    public <T> Runnable addBehavior(T metadata, Consumer<T> task) {
        return AnimationStaticReference.staticReference.addBehavior(metadata, task);
    }

    public void removeBehavior(Runnable r) {
        AnimationStaticReference.staticReference.removeBehavior(r);
    }
}
