package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.location.Location;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.value.ChangeType;

import java.awt.*;


public class Example extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig
                .setFramerate(30)
                .setDisableCodec(true);


    }

    @Override
    public void buildPresentation() {

        var rect= new Rect(Location.at(100,100),Location.at(200,200),Color.magenta);
        var rect2= new Rect(Location.at(100,100),Location.at(200,200),Color.orange);


        add(rect);
        add(rect2);

        var circle = CircleBuilder.aCircle().build();
        add(circle);


        rect.setPositionTo(circle.getBorders().midPoint());
        rect2.setPositionTo(circle.getBorders().midPoint());


        rect.getAngle().change(0.1,seconds(100), ChangeType.CONSTANT_SPEED).executeInBackGround();
        rect2.getAngle().change(-0.1,seconds(100), ChangeType.CONSTANT_SPEED).executeInBackGround();

        var x=circle.changeColor(Color.red,seconds(0.5)).andThen(circle.changeColor(Color.white,seconds(0.5))).repeat(20).executeInBackGround();
        circle.move(200,0).andThen(circle.move(-200,0)).repeat(4).execute();

    }

    public static void main(String[] args) {
        new Example().build();
    }
}

