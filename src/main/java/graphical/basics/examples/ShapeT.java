package graphical.basics.examples;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Fonts;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.effects.T3b1b;
import graphical.basics.task.WaitTask;

import java.awt.*;

import static graphical.basics.presentation.effects.T3b1b.TransformationType.BEST_MATCHING;


public class ShapeT extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true).setFramerate(30);
    }

    @Override
    public void buildPresentation() {
        var GRAY = ColorHolder.hex2Rgb("#403D39");
        var ORANGE = ColorHolder.hex2Rgb("#EB5E28");


        var latex= new LatexGobject("f(x)=x^2", new Point(200,500),Color.WHITE);
        add(latex);

        Animation.strokeAndFill(latex,seconds(1)).execute();

//        getCamera().getScale().setValue(10);
//        getCamera().changeSetPosition(-280,50);

        Animation.t3b1b(latex,new LatexGobject("f(x+1)=x^2+2x+1", new Point(200,500),Color.WHITE),BEST_MATCHING,seconds(1)).execute();


        wait(1).execute();
        cut();
    }

    public static void main(String[] args) {
        new ShapeT().buildPresentation();
    }
}
