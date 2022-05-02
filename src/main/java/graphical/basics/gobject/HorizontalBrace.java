package graphical.basics.gobject;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.task.Task;

import java.awt.*;

public class HorizontalBrace extends Group {


    public enum Placement {
        TOP,
        DOWN
    }

    private final Line main;
    private final Line limitRight;
    private final Line limitLeft;

    private double leght = 0;


    public HorizontalBrace(Location location, double lenght) {
        this.leght = lenght;

        main = new Line(location, location.plus(lenght, 0), Color.white);
        limitLeft = new Line(location.plus(0, -20), location.plus(0, 20), Color.white);
        limitRight = new Line(location.plus(lenght, -20), location.plus(lenght, 20), Color.white);

        add(main);
        add(limitRight);
        add(limitLeft);
    }

    public void expandRight(double amount) {
        main.getP2().incrementX(amount);
        limitRight.changeSetPosition(amount, 0);
        leght += 2;
    }

    public Task expandRightAnimated(double amount, int steps) {
        return main.getP2().move(amount, 0, steps)
                .parallel(limitRight.move(amount, 0, steps))
                .afterConclusion(() -> leght += amount);
    }

    public void expandLeft(double amount) {
        main.getP1().incrementX(-amount);
        limitLeft.changeSetPosition(amount, 0);
        leght += amount;
    }

    public Task expandLeftAnimated(double amount, int steps) {
        return main.getP1().move(-amount, 0, steps)
                .parallel(limitLeft.move(-amount, 0, steps))
                .afterConclusion(() -> leght += amount);
    }

    public void expandCenter(double amount) {
        main.getP2().incrementX(amount / 2);
        main.getP1().incrementX(-amount / 2);
        limitRight.changeSetPosition(amount / 2, 0);
        limitLeft.changeSetPosition(-amount / 2, 0);
        leght += amount;
    }

    public Task expandCenterAnimated(double amount, int steps) {
        return main.getP1().move(-amount / 2, 0, steps)
                .parallel(main.getP2().move(amount / 2, 0, steps))
                .parallel(limitRight.move(amount / 2, 0, steps))
                .parallel(limitLeft.move(-amount / 2, 0, steps))
                .afterConclusion(() -> leght += amount);
    }

    public static HorizontalBrace embrace(Gobject gobject, Placement placement, double space) {
        if (placement == Placement.TOP) {
            var borders = gobject.getBorders();
            return new HorizontalBrace(borders.getL1().plus(0, -space), borders.getwidth());
        } else {
            var borders = gobject.getBorders();
            return new HorizontalBrace(borders.getL1().plus(0, borders.getheight() + space), borders.getwidth());
        }

    }

    public static HorizontalBrace embrace(Gobject gobject) {
        return embrace(gobject, Placement.TOP, 30);
    }

    public static HorizontalBrace embrace(Gobject gobject, Placement placement) {
        return embrace(gobject, placement, 30);
    }


    public Line getMain() {
        return main;
    }

    public Line getLimitRight() {
        return limitRight;
    }

    public Line getLimitLeft() {
        return limitLeft;
    }

    public double getLeght() {
        return leght;
    }

   public StringGobject caption(String s, Placement placement) {

        if (placement == Placement.TOP) {
            return new StringGobject(s, Fonts.JETBRAINS_MONO.deriveFont(30f), this.getBorders().midPoint().plus(0, -10), Color.white);
        } else {
            return new StringGobject(s, Fonts.JETBRAINS_MONO.deriveFont(30f), this.getBorders().midPoint().plus(0, +30), Color.white);
        }

    }
}
