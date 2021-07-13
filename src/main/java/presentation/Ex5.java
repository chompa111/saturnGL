package presentation;

import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.gobject.Ball;
import graphical.basics.gobject.Polygon;
import graphical.basics.location.Point;
import graphical.basics.location.SupplierPoint;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.MorfTransform;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class Ex5 extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    public void buildPresentation() {
        var x = new DoubleHolder(500);
        var poly = new Polygon(Color.yellow, new SupplierPoint(x.getSupplier(), () -> 500.0), new Point(600, 500), new Point(550, 450));
        var ball = new Ball(new Point(200,200),Color.magenta);
        add(ball);
        var value= new DoubleHolder(0);
        add(poly);
        execute( new MorfTransform(poly,ball,seconds(2)));
        execute(new WaitTask(1));
        cut();
    }

    public static void main(String[] args) {
        new Ex5().buildPresentation();
    }
}
