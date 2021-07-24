package presentation;

import graphical.basics.gobject.EffectLens;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Effects;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.WaitTask;

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

        var logo = new SVGGobject("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\src\\main\\resources\\saturn-font-logo.svg");
        var exp = new LatexGobject("\\[ \\lim_{x\\to\\infty \\otimes} \\[ \\prod_{i=a}^{x} f(i) \\] \\]", new Point(100, 300), new Color(200, 0, 150));


        var anel=logo.getGroupExcept("satName","planeta");
        var resto= logo.getGroup("satName","planeta");

        add(resto);
        add(anel);

        anel.init().parallel(resto.init()).execute();


        anel.toGroupGobject().onChildren(Effects::emphasize,4).execute();

       // exp.onChildren(x->x.rotate(2*3.14)).execute();


        //exp.getAng().change(1).execute();



        cut();

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
