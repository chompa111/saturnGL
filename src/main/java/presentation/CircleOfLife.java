package presentation;

import codec.Presentation;
import graphical.basics.behavior.FollowBehavior;
import graphical.basics.gobject.*;
import graphical.basics.gobject.Polygon;
import graphical.basics.location.Point;
import graphical.basics.location.SupplierPoint;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class CircleOfLife extends Presentation {
    @Override
    public void buildPresentation() {
        Circle te = CircleBuilder.aCircle().withColor(Color.magenta).build();
        // add(te);
        Circle circle = new Circle(new Point(500, 500), new DoubleHolder(200), Color.white);
        var angArc = new DoubleHolder(0);
        Arc arc = new Arc(new Point(500, 500), new DoubleHolder(200), angArc, Color.white);
        add(arc);

        execute();
        // add(circle);


        var center = new Point(500, 500);

        var ang = new DoubleHolder(0);
        double radio = 100;
        var point = new SupplierPoint(
                () -> center.getX() + Math.cos(ang.getValue()) * radio,
                () -> center.getY() + Math.sin(ang.getValue()) * radio
        );

        var point2 = new SupplierPoint(
                () -> center.getX() + Math.cos(ang.getValue()) * (radio+100),
                () -> center.getY() + Math.sin(ang.getValue()) * (radio+100)
        );

        var string = new StringGobject(new Point(point2.getX() + 10, point2.getY() - 10), () -> "f(" + (int) (-ang.getValue() * (180 / 3.14)) + ")", Color.white);

        add(new FollowBehavior(string, point2));

        var line = new Line(center, point, Color.red, new DoubleHolder(2));


        var cospoint = new SupplierPoint(point::getX, center::getY);

        var cosLine = new Line(new Point(center.getX(), center.getY()), cospoint, Color.green, new DoubleHolder(2));
        add(cosLine);

        var sinLine = new Line(cospoint, point, Color.blue, new DoubleHolder(2));
        add(sinLine);
        // execute(circle.transform(line));
        add(line);
        add(string);
        var gg = new Group(circle, line, cosLine, sinLine);
        var init = angArc.aceleratedChange(360, seconds(5));
        ang.aceleratedChange(-2 * Math.PI, seconds(5))
              //  .parallel(new WaitTask(seconds(1.5)).andThen(string.move(-115, 0, 30)))
              //  .parallel(new WaitTask(seconds(2.5)).andThen(string.move(0, 50, 30)))
               // .parallel(new WaitTask(seconds(3.5)).andThen(string.move(115, 0, 30)))
                .parallel(init).execute();

        execute(fadeOut(gg));

        var triang = new Polygon(new Color(160, 51, 191),
                685.85134, 632.12096,
                519.93197, 851.56067,
                438.31479, 623.91436,
                685.85134, 632.12096


        );

        execute(arc.transform(triang));

//        execute(new ValueTransform(Arrays.asList(ang), -2 * Math.PI, seconds(3)).parallel(
//                (new ValueTransform(Arrays.asList(circle.zoom),2,seconds(2)))
//        ));

        var circlinho = CircleBuilder.aCircle()
                .build();
        add(circlinho);

        cut();
    }

    public static void main(String[] args) {
        new CircleOfLife().buildPresentation();
    }
}
