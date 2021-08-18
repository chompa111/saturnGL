package graphical.basics.examples;

import graphical.basics.gobject.TextGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.WaitTask;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class FormulaEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {


        DoubleHolder vh = new DoubleHolder(2);

        var plainFormula = new TextGobject("ax^2+bx+c=0", new Point(200, 500), new Color(187, 174, 100));

        var plainFormula2 = new TextGobject("ax^2+b(y-z)+c=0", new Point(200, 500), new Color(187, 174, 100));
       // add(plainFormula2);
        //LatexGobject.indexsize(plainFormula2);
        var yminuszExcept=plainFormula2.subGroupExept(5,6,7,8,9);
        var yminusz=plainFormula2.subGroup(5,6,7,8,9);

        //plainFormula2.subGroupExept()

        add(plainFormula);
        Animation.strokeAndFill(plainFormula,seconds(1)).execute();
        Positioning.alignAll(plainFormula.subGroupExept(5).getGobjects(),yminuszExcept.getGobjects())
                .parallel(
                        plainFormula.subGroup(5).transform(yminusz)
                                .parallel( plainFormula.subGroup(5).changeColor(new Color(0,0,0,0),4))
                ).execute();

        Animation.emphasize(yminusz).execute();
        add(plainFormula2);
        remove(yminusz);
        remove(plainFormula);

        var plainFormula3 = new TextGobject("ax^2+c=-b(y-z)", new Point(200, 500), new Color(187, 174, 100));
//        add(plainFormula3);
//
//        LatexGobject.indexsize(plainFormula3);

        new WaitTask(seconds(1)).execute();

//        LatexGobject.indexsize(plainFormula3);
//        LatexGobject.indexsize(plainFormula2);

        var a=plainFormula2.subGroup(0,1,2,3,11,12,4,5,6,7,8,9);
        var aExp=plainFormula2.subGroupExept(0,1,2,3,11,12,4,5,6,7,8,9);
       Animation.fadeOut(aExp).execute();
        var minus=plainFormula3.subGroup(6);
        add(minus);
        Positioning.alignAll(a.getGobjects(),plainFormula3.subGroupExept(6).getGobjects())
                .parallel( Animation.strokeAndFill(minus,seconds(1)))
                .execute();

        new WaitTask(1).execute();
        cut();
    }

    public static void main(String[] args) {
        new FormulaEx().buildPresentation();
    }
}
