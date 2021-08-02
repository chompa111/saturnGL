package graphical.basics.gobject.shape;

import graphical.basics.gobject.*;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class ShapeCircleEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(60);
        presentationConfig.setDisableCodec(true);

    }

    @Override
    public void buildPresentation() {
        Circle circle= CircleBuilder.aCircle().withColor(Color.green).build();
        Circle circle2= CircleBuilder.aCircle().withColor(Color.orange).build();

        circle2.changeSetPosition(0,200);

        add(circle);
        add(circle2);
        Effects.init(circle).parallel(Effects.init(circle2)).execute();



        cut();
    }

    public static void main(String[] args) {
        new ShapeCircleEx().buildPresentation();
    }
}
