package graphical.basics.animations.sierpinsky;

import graphical.basics.gobject.Fonts;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.JavaHilighter;
import graphical.basics.gobject.Text;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.Task;

import java.awt.*;

import static graphical.basics.gobject.JavaHilighter.INTELLIJ_GRAY;

public class Carpet extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
      //  presentationConfig.setDisableCodec(true);
        presentationConfig.setWidth(1920);
        presentationConfig.setHeight(1080);
    }

    @Override
    protected void buildPresentation() {

       // switchOff();

        sierpinsky(Location.at((1920 / 2.0) - 450, 90), 900, 3).execute();
//        removeAll();
//       // wait(seconds(10)).execute();

        removeAll();

        var txt = new Text(INTELLIJ_GRAY, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(200, 500));

        add(txt);
        txt.newLine("void tapete(){");
        txt.newLine(" ");
        txt.newLine("   for(int i=0;i<200;i++){");
        txt.newLine("      funcaoDespirocada(int a8);");
        txt.newLine("   }");
        txt.newLine("}");



        txt.setPositionTo(Location.at(1920 / 2.0, 1080 / 2.0));

        new JavaHilighter().colorize(txt);

        txt.typeEffect().execute();

        var carpet = sierpinskyGobject(Location.at((1920 / 2.0) - 500, 40), 500, 3);
        carpet.setPositionTo(Location.at(1920 / 2.0, 1080 / 2.0));
       // carpet.scale.setValue(0.5);
//        add(carpet);
//
//        var aux = new SVGGobject("C:\\Users\\PICHAU\\Desktop\\icone.svg").toGroupGobject();
//        aux.setPositionTo(Location.at(1920/2.0,1080/2.0));
//
//

        wait(seconds(3)).execute();
        Animation.t3b1b(txt, carpet, seconds(2)).execute();


        //carpet.move(300,300).execute();
       // txt.transform(carpet,seconds(3)).execute();


    }

    private Task sierpinsky(Location l1, double width, int depth) {
        if (depth < 0) return wait(1);

        var newWidth = width / 3;
        //
        Color color = Color.getHSBColor(0.5f + (float) ((l1.getX() - l1.getY()) / 6000.0), 1, 1);
        var r = new Rect(l1.plus(newWidth, newWidth), l1.plus(2 * newWidth, 2 * newWidth), color);

        var mainRectTask = new ContextSetupTask(() -> {
            add(r);
            return Animation.strokeAndFill(r, seconds(0.80));
        });


        var chieldTask = sierpinsky(l1, newWidth, depth - 1)
                .parallel(sierpinsky(l1.plus(newWidth, 0), newWidth, depth - 1))
                .parallel(sierpinsky(l1.plus(2 * newWidth, 0), newWidth, depth - 1))
                .parallel(sierpinsky(l1.plus(2 * newWidth, newWidth), newWidth, depth - 1))
                .parallel(sierpinsky(l1.plus(2 * newWidth, 2 * newWidth), newWidth, depth - 1))
                .parallel(sierpinsky(l1.plus(newWidth, 2 * newWidth), newWidth, depth - 1))
                .parallel(sierpinsky(l1.plus(0, 2 * newWidth), newWidth, depth - 1))
                .parallel(sierpinsky(l1.plus(0, newWidth), newWidth, depth - 1));


        return mainRectTask.andThen(chieldTask).andThen(wait(10+(int)(Math.random()*30))).andThen(() -> Animation.fadeoutGrow(r,20));

    }

    private Group sierpinskyGobject(Location l1, double width, int depth) {


        var newWidth = width / 3;

        var group = new Group();
        Color color = Color.getHSBColor(0.5f + (float) ((l1.getX() - l1.getY()) / 8000.0), 1, 1);
        var r = new Rect(l1.plus(newWidth, newWidth), l1.plus(2 * newWidth, 2 * newWidth), color);
        group.add(r);

        if (depth < 0) {
            return group;
        }

        group.add(sierpinskyGobject(l1, newWidth, depth - 1));
        group.add(sierpinskyGobject(l1.plus(newWidth, 0), newWidth, depth - 1));
        group.add(sierpinskyGobject(l1.plus(2 * newWidth, 0), newWidth, depth - 1));
        group.add(sierpinskyGobject(l1.plus(2 * newWidth, newWidth), newWidth, depth - 1));
        group.add(sierpinskyGobject(l1.plus(2 * newWidth, 2 * newWidth), newWidth, depth - 1));
        group.add(sierpinskyGobject(l1.plus(newWidth, 2 * newWidth), newWidth, depth - 1));
        group.add(sierpinskyGobject(l1.plus(0, 2 * newWidth), newWidth, depth - 1));
        group.add(sierpinskyGobject(l1.plus(0, newWidth), newWidth, depth - 1));


        return group;

    }


    public static void main(String[] args) {
        new Carpet().build();
    }
}
