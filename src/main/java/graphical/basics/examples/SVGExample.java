package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.gobject.StringGobject;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.WaitTask;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class SVGExample extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(60);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {


        DoubleHolder vh = new DoubleHolder(2);

        var formula_rect = new LatexGobject("f(x)=a.x+b", new Point(200, 500), new Color(187, 174, 100));

        add(formula_rect);
        LatexGobject.indexsize(formula_rect);

        Animation.strokeAndFill(formula_rect, seconds(2)).execute();

        var parenthesisL = new LatexGobject("(f(x))^2=(a.x+b)^2+c", new Point(200, 500), new Color(187, 174, 100));
        //add(parenthesisL);
        var sub = parenthesisL.subGroupExept(0, 5, 6, 8, 14, 15);
        var f = parenthesisL.subGroup(0, 5, 6, 8, 14, 15,16,17);
        Positioning.alignAll(formula_rect.getGobjects(), sub.getGobjects())
                .parallel(new ContextSetupTask(() -> {
                    add(f);
                    return new WaitTask(seconds(0.5)).andThen(Animation.strokeAndFill(f, seconds(1)));
                }))
                .execute();


        formula_rect.subGroup(5).changeColor(Color.red)
                .parallel(formula_rect.subGroup(9).changeColor(Color.green))
                .execute();

        Animation.emphasize(f).execute();

        cut();
    }

    public static void main(String[] args) {
        new SVGExample().buildPresentation();
    }
}
