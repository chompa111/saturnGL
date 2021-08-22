package graphical.basics.examples;

import graphical.basics.gobject.Circle;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.TextGobject;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.location.Location;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.effects.T3b1b;
import graphical.basics.task.WaitTask;
import graphical.basics.value.DoubleHolder;

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

//        var rect = new Rect(new Point(100,100), new Point(300,300),Color.orange);
//        add(rect);
//
//        rect.moveTo(Location.at(500,500)).execute();

        var latex= new TextGobject("f(x)=x^2+2x+1", Location.at(100,500),Color.white);
        var latex2= new TextGobject("f(x)=(x+1)^2", Location.at(100,500),Color.white);
        var latex3= new TextGobject("f(x)=\\frac{(x+1)^2}{x}", Location.at(100,500),Color.white);
       // switchOff();
        add(latex);
        Animation.strokeAndFill(latex,seconds(1)).execute();

        Animation.t3b1b(latex,latex2, T3b1b.TransformationType.BEST_MATCHING,seconds(1)).execute();
        wait(seconds(1)).execute();
        Animation.t3b1b(latex2,latex3, T3b1b.TransformationType.BEST_MATCHING,seconds(1)).execute();

        var latex4= new TextGobject("f(u+1)=\\frac{((u+1)+1)^2}{(u+1)}", Location.at(100,500),Color.white);

        wait(seconds(1)).execute();
        Animation.t3b1b(latex3,latex4, T3b1b.TransformationType.BEST_MATCHING,seconds(1)).execute();

        //TextGobject.indexsize(latex3);



        latex3.subGroup(6,12).changeColor(Color.red).andThen(latex3.subGroup(6,12).changeColor(Color.white)).repeat(10).executeInBackGround();

        new WaitTask(seconds(10)).execute();




    }

    public static void main(String[] args) {
        new Example().build();
    }
}

