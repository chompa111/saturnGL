package presentation;

import graphical.basics.ColorHolder;
import graphical.basics.Pivot;
import graphical.basics.gobject.EffectLens;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Effects;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorListTranform2;
import graphical.basics.task.transformation.gobject.ColorTranform2;

import java.awt.*;

import static graphical.basics.gobject.latex.lixao.Latex.generateExp;

public class Latex extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(60)
                .setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {

        var logo = new SVGGobject("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\src\\main\\resources\\azuis.svg");


       add(logo);

       logo.toGroupGobject().onChildren(Effects::emphasize,2).execute();
       logo.init().execute();




       new WaitTask(1).execute();
       cut();


        // exp.onChildren(x->x.rotate(2*3.14)).execute();


        //exp.getAng().change(1).execute();

        // cut();

//        var list = generateExp("Bola", new Point(200, 300), Color.white);
//        var sub= new Group(list);
//
//        var circle = CircleBuilder.aCircle().withColor(Color.magenta).build();
//
//
//
//
//        var behaviorSub=circle.asSubtitle(sub,0);
//        add(behaviorSub);
//        add(circle);
//
//        add(sub);
//        Effects.init(circle).parallel(Effects.init(sub)).execute();
//
//        circle.getRadius().aceleratedChange(200).execute();
//        circle.move(200,0).execute();


    }

    public static void main(String[] args) {
        new Latex().buildPresentation();
    }
}
