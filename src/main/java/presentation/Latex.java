package presentation;

import graphical.basics.ColorHolder;
import graphical.basics.Pivot;
import graphical.basics.gobject.Circle;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.EffectLens;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.SVGGobject2;
import graphical.basics.location.Point;
import graphical.basics.presentation.Effects;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorListTranform2;
import graphical.basics.task.transformation.gobject.ColorTranform2;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.util.Random;

import static graphical.basics.gobject.latex.lixao.Latex.generateExp;
import static graphical.basics.presentation.Positioning.Reference.*;

public class Latex extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30)
                .setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {



        var A= new LatexGobject(new Font("JetBrains Mono",Font.ITALIC,70),"1",new Point(100,500), Color.white);
        var B= new LatexGobject(new Font("JetBrains Mono",Font.ITALIC,70),"2",new Point(150,500), Color.BLUE);

        add(A);
        add(B);

        A.init().parallel(B.init()).execute();

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

    void pepe(Gobject g, int prof) {
        if (prof == 0) return;
        if (g instanceof Circle) {
            // hora do quadrado
            var d = ((Circle) g).getRadius().getValue() / Math.sqrt(2);
            Rect rect = new Rect(new Point(0, 0), new Point(d, d), ColorHolder.randomColor());
            Positioning.align(rect, g, CENTER);
            add(rect);
            rect.init().execute();
            pepe(rect, prof - 1);
        }
        if (g instanceof Rect) {
            //hora do circulo
            var rect = ((Rect) g);
            var d = rect.getBorders().getL2().getX() - rect.getBorders().getL1().getX();
            var circle=CircleBuilder.aCircle().withRadius(d).withColor(ColorHolder.randomColor()).build();
            Positioning.align(rect, g, CENTER);
            add(circle);
            circle.init().execute();
            pepe(circle, prof - 1);
        }
    }

    public static void main(String[] args) {
        {
            String fonts[] =
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

            for (int i = 0; i < fonts.length; i++) {
                System.out.println(fonts[i]);
            }
        }
        new Latex().buildPresentation();
    }
}
