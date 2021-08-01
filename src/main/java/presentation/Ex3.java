package presentation;

import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.gobject.Ball;
import graphical.basics.ColorHolder;
import graphical.basics.location.Point;
import graphical.basics.location.SupplierPoint;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.PositionTransform;

import java.awt.*;

public class Ex3 extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    public void buildPresentation() {
        var b1 = new Ball(new Point(50, 500), 0.0, 0.0, Color.yellow);
        add(b1);
        execute(processBall(b1, 0, 3));

        cut();
    }

    Task processBall(Ball ball, int pepe, int max) {
        if (pepe == max) return new WaitTask(1);
        var location = ball.getRefereceLocations().get(0);
        var b1 = new Ball(new SupplierPoint(0,0,location::getX,location::getY), 0.0, 0.0, ball.getColors().get(0).getColor());
        add(b1);
        var b2 = new Ball(new SupplierPoint(0,0,location::getX,location::getY), 0.0, 0.0, ball.getColors().get(0).getColor());
        add(b2);

        var t1 = new PositionTransform(b1, 100, (max - pepe) * 50, seconds(1));
        var t2 = new PositionTransform(b2, 100, -(max - pepe) * 50, seconds(1));

        var c1 = new ColorTranform(b1, ColorHolder.randomColor(), seconds(1));
        var c2 = new ColorTranform(b2, ColorHolder.randomColor(), seconds(1));

        var r1 = processBall(b1, pepe + 1, max);
        var r2 = processBall(b2, pepe + 1, max);

        return new ParalelTask(t1, t2, c1, c2).wait(1).andThen(new ParalelTask(r1, r2).wait(1));
    }


    public static void main(String[] args) {
        new Ex3();
    }
}
