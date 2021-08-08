package graphical.basics.examples;

import graphical.basics.gobject.Circle;
import graphical.basics.location.Point;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.value.DoubleHolder;

import java.awt.*;


public class Example extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig
                .setFramerate(10)
                .setDisableCodec(false);

    }

    @Override
    public void buildPresentation() {
        var center = new Point(100, 500);
        var radius = new DoubleHolder(100);
        var color = new Color(255, 0, 255); // this is a awt color
        var circle = new Circle(center, radius, color);
        add(circle);
        var taskMove=circle.move(400,0,seconds(2));
        var taskChangeColor=circle.changeColor(new Color(255,0,0),seconds(2));
        taskMove.parallel(taskChangeColor).execute();


    }

    public static void main(String[] args) {
        new Example().buildPresentation();
    }
}

