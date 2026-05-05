package graphical.basics.miscelaneous;

import codec.CodecType;
import codec.engine.EngineType;
import graphical.basics.gobject.TextGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.SupplierTask;
import graphical.basics.task.WaitTask;

import java.awt.*;

public class BaskaraR extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
     //   presentationConfig.setDisableCodec(true);
        presentationConfig.setFramerate(6);
        presentationConfig.setCodec(CodecType.RAW_IMAGE);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    public void buildAnimation() {

        var mainFormula = new TextGobject("x^2+2x+1=0", new Point(100, 100), Color.white);


        add(mainFormula);
        //LatexGobject.indexsize(mainFormula);
        Animations.strokeAndFill(mainFormula, seconds(1)).execute();

        var mainFormulaCopy = new TextGobject("x^2+2x+1=0", new Point(100, 100), Color.white);
        var simplificada = new TextGobject("(x+1)^2=0", new Point(100, 200), Color.white);

//        add(simplificada);
//        LatexGobject.indexsize(simplificada);

        add(mainFormulaCopy);
        var restoSimplificada = simplificada.subGroupExept(1, 2, 3, 6, 7);
        Positioning.alignAll(mainFormulaCopy.subGroup(0, 5, 6, 7, 8).getGobjects(), simplificada.subGroup(1, 2, 3, 6, 7).getGobjects())
                .parallel(new WaitTask(seconds(0.5)).andThen(new SupplierTask(() -> {
                    add(restoSimplificada);
                    return Animations.strokeAndFill(restoSimplificada, seconds(1));
                })))
                .execute();

        remove(mainFormulaCopy);
        remove(restoSimplificada);
        add(simplificada);

        var mainSimplificada = new TextGobject("x+1=0", new Point(114, 208), Color.white);

      //  add(mainSimplificada);
        Positioning.alignAll(simplificada.subGroup(1,2,3,6,7).getGobjects(),mainSimplificada.getGobjects())
                .parallel( simplificada.subGroupExept(1,2,3,6,7).changeColor(new Color(0,0,0,0)))
                .execute();

        remove(simplificada);
        add(mainSimplificada);

        var fim= new TextGobject("x=-1", new Point(114, 208), Color.white);

        //add(fim);

        new WaitTask(seconds(1)).execute();

        Positioning.alignAll(mainSimplificada.subGroup(0,3,2).getGobjects(),fim.subGroup(0,1,3).getGobjects())
                .parallel( mainSimplificada.subGroupExept(0,3,2).changeColor(new Color(0,0,0,0)).parallel(mainSimplificada.subGroup(1).transform(fim.subGroup(2))))
                .execute();

        remove(mainSimplificada);
        add(fim);

        new WaitTask(seconds(1)).execute();

        Animations.emphasize(fim).execute();




        new WaitTask(1).execute();
        cut();
    }


    public static void main(String[] args) {
        new BaskaraR().build();
    }
}
