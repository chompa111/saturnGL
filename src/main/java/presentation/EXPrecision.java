package presentation;

import codec.Presentation;
import graphical.basics.gobject.Circle;
import graphical.basics.gobject.Line;
import graphical.basics.location.Point;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.MorfTransform;
import graphical.basics.task.transformation.value.ValueTransform;
import graphical.basics.value.DoubleHolder;

import java.awt.*;
import java.util.Arrays;

public class EXPrecision extends Presentation {
    @Override
    public void buildPresentation() {
        var line = new Line(new Point(200, 500), new Point(500, 500), Color.red, new DoubleHolder(10));
        add(line);

        var raio= new DoubleHolder(150);
        var circle = new Circle(new Point(350, 500),raio, Color.cyan);
        //add(circle);
        execute(new WaitTask(seconds(0.5)));
        execute(new MorfTransform(line, circle, seconds(1)));
        execute(new ValueTransform(Arrays.asList(raio),100,seconds(0.5)));
        cut();
    }

    public static void main(String[] args) {
        new EXPrecision().buildPresentation();
    }
}
