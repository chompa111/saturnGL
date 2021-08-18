package graphical.basics.examples;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.TextGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

import static graphical.basics.presentation.effects.T3b1b.TransformationType.BEST_MATCHING;


public class ShapeT extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true).setFramerate(32);
    }

    @Override
    public void buildPresentation() {
        var GRAY = ColorHolder.hex2Rgb("#403D39");
        var ORANGE = ColorHolder.hex2Rgb("#EB5E28");


        var latex = new TextGobject("g(x)", new Point(200, 500), Color.WHITE);
        add(latex);

        Animation.strokeAndFill(latex, seconds(1)).execute();

//        getCamera().getScale().setValue(10);
//        getCamera().changeSetPosition(-280,50);
        var latex2 = new TextGobject("ax_i+b_i", new Point(200, 500), Color.WHITE);
        wait(seconds(0.5))
                .andThen(() -> Animation.t3b1b(latex, latex2, BEST_MATCHING, seconds(1)))
                .step(this::cut)
                .andThen(wait(seconds(0.5)))
                .andThen(() -> Animation.t3b1b(latex2, latex, BEST_MATCHING, seconds(1)))
                .step(this::cut)
                .repeat(3)
                .execute();


        wait(1).execute();
        cut();
    }

    public static void main(String[] args) {
        new ShapeT().buildPresentation();
    }
}
