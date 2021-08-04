package graphical.basics.presentation;

import graphical.basics.gobject.struct.Char2;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.Point;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;

import java.util.ArrayList;
import java.util.List;

import static graphical.basics.presentation.Positioning.Reference.CENTER;

public class Positioning {

    public static enum Reference {
        CENTER, LEFT, RIGHT, TOP, BOTTOM
    }

    public static class animation {
        public static Task alignX(Gobject a, Reference referenceA, Gobject b, Reference referenceB) {
            return a.move(getAlignDeltaX(a, referenceA, b, referenceB), 0);
        }

        public static Task alignY(Gobject a, Reference referenceA, Gobject b, Reference referenceB) {
            return a.move(0, getAlignDeltaY(a, referenceA, b, referenceB));
        }

        public static Task align(Gobject a, Gobject b, Reference reference) {
            switch (reference) {
                case BOTTOM:
                    return alignX(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER)
                            .parallel(alignY(a, Positioning.Reference.TOP, b, Positioning.Reference.BOTTOM));
                case RIGHT:
                    return alignX(a, Positioning.Reference.LEFT, b, Positioning.Reference.RIGHT)
                            .parallel(alignY(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER));

                case LEFT:
                    return alignX(a, Positioning.Reference.RIGHT, b, Positioning.Reference.LEFT)
                            .parallel(alignY(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER));
                case TOP:
                    return alignX(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER)
                            .parallel(alignY(a, Positioning.Reference.BOTTOM, b, Positioning.Reference.TOP));
                case CENTER:
                   return alignX(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER)
                            .parallel(alignY(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER));
            }
            return new WaitTask(1);
        }
    }

    public static void align(Gobject a, Gobject b, Reference reference) {
        switch (reference) {
            case BOTTOM:
                Positioning.alignX(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER);
                Positioning.alignY(a, Positioning.Reference.TOP, b, Positioning.Reference.BOTTOM);
                break;
            case RIGHT:
                Positioning.alignX(a, Positioning.Reference.LEFT, b, Positioning.Reference.RIGHT);
                Positioning.alignY(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER);
                break;
            case LEFT:
                Positioning.alignX(a, Positioning.Reference.RIGHT, b, Positioning.Reference.LEFT);
                Positioning.alignY(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER);
                break;
            case TOP:
                Positioning.alignX(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER);
                Positioning.alignY(a, Positioning.Reference.BOTTOM, b, Positioning.Reference.TOP);
                break;
            case CENTER:
                Positioning.alignX(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER);
                Positioning.alignY(a, Positioning.Reference.CENTER, b, Positioning.Reference.CENTER);
        }
    }


    public static void alignX(Gobject a, Reference referenceA, Gobject b, Reference referenceB) {
        a.changeSetPosition(getAlignDeltaX(a, referenceA, b, referenceB), 0);
    }

    public static void alignY(Gobject a, Reference referenceA, Gobject b, Reference referenceB) {
        a.changeSetPosition(0, getAlignDeltaY(a, referenceA, b, referenceB));
    }

    public static double getAlignDeltaX(Gobject a, Reference referenceA, Gobject b, Reference referenceB) {
        double bx = getPositionX(b, referenceB);
        double ax = getPositionX(a, referenceA);
        return bx - ax;
    }

    public static double getAlignDeltaY(Gobject a, Reference referenceA, Gobject b, Reference referenceB) {
        double by = getPositionY(b, referenceB);
        double ay = getPositionY(a, referenceA);
        return by - ay;
    }


    public static double getPositionX(Gobject gobject, Reference reference) {
        switch (reference) {
            case CENTER:
                return gobject.getBorders().midPoint().getX();
            case LEFT:
                return gobject.getBorders().getL2().getX();
            case RIGHT:
                return gobject.getBorders().getL1().getX();
            default:
                throw new RuntimeException("this aligment does not make sense");
        }
    }

    public static double getPositionY(Gobject gobject, Reference reference) {
        switch (reference) {
            case CENTER:
                return gobject.getBorders().midPoint().getY();
            case TOP:
                return gobject.getBorders().getL1().getY();
            case BOTTOM:
                return gobject.getBorders().getL2().getY();
            default:
                throw new RuntimeException("this aligment does not make sense");
        }
    }

    public static Location getPoint(Gobject gobject, Reference r1, Reference r2) {
        return new Point(getPositionX(gobject, r1), getPositionY(gobject, r2));
    }

    public static Task alignAll(List<Gobject> l1, List<Gobject> l2) {
        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < l1.size(); i++) {
            taskList.add(Positioning.animation.align(l1.get(i), l2.get(i), CENTER));
        }

        return new ParalelTask(taskList);
    }

}
