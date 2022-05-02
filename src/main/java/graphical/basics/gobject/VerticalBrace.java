package graphical.basics.gobject;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.task.Task;

import java.awt.*;

public class VerticalBrace extends Group {


    public enum Placement {
        LEFT,
        RIGHT
    }

    private final Line main;
    private final Line limitTop;
    private final Line limitDown;

    private double leght = 0;


    public VerticalBrace(Location location, double lenght) {
        this.leght = lenght;

        main = new Line(location, location.plus(0, lenght), Color.white);
        limitDown = new Line(location.plus(-20, 0), location.plus(20, 0), Color.white);
        limitTop = new Line(location.plus(-20, lenght), location.plus(20, lenght), Color.white);

        add(main);
        add(limitTop);
        add(limitDown);
    }

    public void expandDown(double amount) {
        main.getP2().incrementY(amount);
        limitDown.changeSetPosition(0, amount);
        leght += amount;
    }

    public Task expandDownAnimated(double amount, int steps) {
        return main.getP2().move(0, amount, steps)
                .parallel(limitDown.move(0, amount, steps))
                .afterConclusion(() -> leght += amount);
    }

    public void expandTop(double amount) {
        main.getP1().incrementY(-amount);
        limitTop.changeSetPosition(0, -amount);
        leght += amount;
    }

    public Task expandTopAnimated(double amount, int steps) {
        return main.getP1().move(0, -amount, steps)
                .parallel(limitTop.move(0, -amount, steps))
                .afterConclusion(() -> leght += amount);
    }

    public void expandCenter(double amount) {
        main.getP2().incrementY(amount / 2);
        main.getP1().incrementY(-amount / 2);
        limitTop.changeSetPosition(0, amount / 2);
        limitDown.changeSetPosition(0, -amount / 2);
        leght += amount;
    }

    public Task expandCenterAnimated(double amount, int steps) {
        return main.getP1().move(0, -amount / 2, steps)
                .parallel(main.getP2().move(0, amount / 2, steps))
                .parallel(limitDown.move(0, amount / 2, steps))
                .parallel(limitTop.move(0, -amount / 2, steps))
                .afterConclusion(() -> leght += amount);
    }

    public static VerticalBrace embrace(Gobject gobject, Placement placement, double space) {
        if (placement == Placement.LEFT) {
            var borders = gobject.getBorders();
            return new VerticalBrace(borders.getL1().plus(-space, 0), borders.getheight());
        } else {
            var borders = gobject.getBorders();
            return new VerticalBrace(borders.getL1().plus(borders.getwidth() + space, 0), borders.getheight());
        }

    }

    public static VerticalBrace embrace(Gobject gobject) {
        return embrace(gobject, Placement.RIGHT, 30);
    }

    public static VerticalBrace embrace(Gobject gobject, Placement placement) {
        return embrace(gobject, placement, 30);
    }


    public Line getMain() {
        return main;
    }

    public Line getLimitTop() {
        return limitTop;
    }

    public Line getLimitDown() {
        return limitDown;
    }

    public double getLeght() {
        return leght;
    }
}
