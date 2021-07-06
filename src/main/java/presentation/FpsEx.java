package presentation;

import codec.Presentation;
import graphical.basics.gobject.Ball;
import graphical.basics.gobject.Group;
import graphical.basics.location.Point;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.PositionTransform;

import java.awt.*;

public class FpsEx extends Presentation {
    @Override
    public void buildPresentation() {
        var ball1 = new Ball(new Point(100, 500), (Math.random() * 20) - 10, (Math.random() * 20) - 10, Color.green);
        var ball2 = new Ball(new Point(100, 700), (Math.random() * 20) - 10, (Math.random() * 20) - 10, Color.magenta);
        var grupo= new Group(ball1,ball2);
        add(grupo);
        var esquerda = PositionTransform.delayed(grupo, -900, 0, seconds(1.0),2);
        var direita =  PositionTransform.delayed(grupo, 900, 0, seconds(1.0),2);
        var turnRed=new ColorTranform(grupo,Color.red,seconds(1.0));
        var turnGreen=new ColorTranform(grupo,Color.green,seconds(1.0));

        execute(paralel(direita,turnRed)
                .andThen(paralel(esquerda,turnGreen))
                .repeat(1));

        cut();

    }

    public static void main(String[] args) {
        new FpsEx();


    }
}
