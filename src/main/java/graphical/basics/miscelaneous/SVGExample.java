package graphical.basics.miscelaneous;

import graphical.basics.gobject.TextGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.SupplierTask;
import graphical.basics.task.WaitTask;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class SVGExample extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(60);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildAnimation() {


        DoubleHolder vh = new DoubleHolder(2);

        var formula_rect = new TextGobject("f(x)=a.x+b", new Point(200, 500), new Color(187, 174, 100));

        add(formula_rect);
        TextGobject.indexsize(formula_rect);

        Animations.strokeAndFill(formula_rect, seconds(2)).execute();

        var parenthesisL = new TextGobject("(f(x))^2=(a.x+b)^2+c", new Point(200, 500), new Color(187, 174, 100));
        //add(parenthesisL);
        var sub = parenthesisL.subGroupExept(0, 5, 6, 8, 14, 15);
        var f = parenthesisL.subGroup(0, 5, 6, 8, 14, 15,16,17);
        Positioning.alignAll(formula_rect.getGobjects(), sub.getGobjects())
                .parallel(new SupplierTask(() -> {
                    add(f);
                    return new WaitTask(seconds(0.5)).andThen(Animations.strokeAndFill(f, seconds(1)));
                }))
                .execute();


        formula_rect.subGroup(5).changeColor(Color.red)
                .parallel(formula_rect.subGroup(9).changeColor(Color.green))
                .execute();

        Animations.emphasize(f).execute();

        cut();
    }

    public static void main(String[] args) {
        new SVGExample().buildAnimation();
    }
}
