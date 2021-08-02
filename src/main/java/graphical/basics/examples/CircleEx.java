package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.WaitTask;

import java.awt.*;

public class CircleEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {

        var circle = CircleBuilder.aCircle().build();
        var circle2 = CircleBuilder.aCircle().withCenter(200, 200).withColor(Color.magenta).build();

        var group = new Group(circle, circle2);
        add(group);
        Animation.fadeInGrow(group, seconds(1)).execute();

        group.getAngle().change(3.1415).execute();
        group.getScale().change(-0.5).execute();

        new WaitTask(1).execute();
        cut();

    }

    public static void main(String[] args) {
        new CircleEx().buildPresentation();
    }
}
