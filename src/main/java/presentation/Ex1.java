package presentation;

import codec.Presentation;
import graphical.basics.gobject.Ball;
import graphical.basics.location.Point;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.PositionTransform;

import java.awt.*;

public class Ex1 extends Presentation {
    @Override
    public void buildPresentation() {

        var ball = new Ball(new Point(100, 100), (Math.random() * 20) - 10, (Math.random() * 20) - 10,new Color(130, 23, 23));
        var ball2 = new Ball(new Point(100, 500), (Math.random() * 20) - 10, (Math.random() * 20) - 10, Color.magenta);
        add(ball);
        add(ball2);

        var changepositionb2 = new PositionTransform(ball2, 900, 0, seconds(0.5));
        var changepositionb22 = new PositionTransform(ball2, -900, 0, seconds(0.5));

        var changecolor = new ColorTranform(ball, Color.GRAY, seconds(0.5));
        var changeposition = new PositionTransform(ball, 900, 0, seconds(0.5));
        var changeposition2 = new PositionTransform(ball, 0, 900, seconds(0.5));
        var changeposition3 = new PositionTransform(ball, -900, 0, seconds(0.5));
        var changeposition4 = new PositionTransform(ball, 0, -900, seconds(0.5));

        var task =changeposition.andThen(changeposition2).andThen(changeposition3).andThen(changeposition4).repeat(3);
        var task2 =  changepositionb2.andThen(changepositionb22).repeat(2);
        execute(task,task2);
        cut();

    }

    public static void main(String[] args) {
        new Ex1().buildPresentation();
    }
}
