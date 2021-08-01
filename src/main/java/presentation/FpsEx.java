package presentation;

import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.gobject.Ball;
import graphical.basics.gobject.Group;
import graphical.basics.location.Point;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.PositionTransform;

import java.awt.*;

public class FpsEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    public void buildPresentation() {
        var ball1 = new Ball(new Point(100, 500), (Math.random() * 20) - 10, (Math.random() * 20) - 10, Color.green);
        var ball2 = new Ball(new Point(100, 700), (Math.random() * 20) - 10, (Math.random() * 20) - 10, Color.magenta);
        var grupo= new Group(ball1,ball2);
        add(grupo);


        cut();

    }

    public static void main(String[] args) {
        new FpsEx();


    }
}
