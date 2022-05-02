package graphical.basics.animations.sierpinsky;

import codec.engine.EngineType;
import graphical.basics.gobject.*;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.Task;

import java.awt.*;

import static graphical.basics.gobject.HorizontalBrace.Placement.DOWN;
import static graphical.basics.gobject.HorizontalBrace.Placement.TOP;
import static graphical.basics.gobject.JavaHilighter.INTELLIJ_GRAY;

public class Carpet extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        //  presentationConfig.setDisableCodec(true);
        presentationConfig.setWidth(1920);
        presentationConfig.setHeight(1080);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildPresentation() {

        // switchOff();

//        sierpinsky(Location.at((1920 / 2.0) - 450, 90), 900, 3).execute();
////        removeAll();
////       // wait(seconds(10)).execute();
//
//        removeAll();
//
//        var txt = new Text(INTELLIJ_GRAY, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(200, 500));
//
//        add(txt);
//        txt.newLine("void tapete(){");
//        txt.newLine(" ");
//        txt.newLine("   for(int i=0;i<200;i++){");
//        txt.newLine("      funcaoDespirocada(int a8);");
//        txt.newLine("   }");
//        txt.newLine("}");
//
//
//
//        txt.setPositionTo(Location.at(1920 / 2.0, 1080 / 2.0));
//
//        new JavaHilighter().colorize(txt);
//
//        txt.typeEffect().execute();

//        var carpet = sierpinskyGobject(Location.at((1920 / 2.0) - 500, 40), 500, 5);
//        add(carpet);
//        carpet.setPositionTo(Location.at(1920 / 2.0, 1080 / 2.0));
//
//        Animation.fadeInGrow(carpet, seconds(1)).execute();
//
//        wait(seconds(10)).execute();
//        cut();
//
//
//        var t = new StringGobject("Tapete de Sierpinsky", Fonts.COMPUTER_MODERN_I.deriveFont(40f), carpet.getBorders().midPoint().plus(-220, -270), Color.white);
//
//        add(t);
//        Animation.strokeAndFill(t, seconds(1)).execute();
//
//        wait(seconds(10)).execute();
//
//        Animation.fadeOut(t, seconds(1)).execute();
//
//        cut();
//
//        var part = carpet.getGobjects().get(1);
//        add(part);
//
//        var mid = carpet.getBorders().midPoint().copy();
//
//        carpet.remove(part);
//
//        Animation.fadeOut(carpet, seconds(2)).execute();
//        remove(carpet);
//        wait(seconds(0.5)).execute();
//
//
//        part.moveTo(mid,seconds(1)).execute();
//
//        wait(seconds(0.5)+1).execute();
//
//        part.getScale().change(2,seconds(2)).execute();


        var carpet = sierpinskyGobject(Location.at((1920 / 2.0) - 500, 40), 500, 4);
        add(carpet);
        carpet.setPositionTo(Location.at(1920 / 2.0, 1080 / 2.0));

//        var lupa = new Magnifier(Location.at(300,300).plus(300,0),Location.at(400,400).plus(300,0));
//
//        add(lupa);
//
//        lupa.getScale().setValue(1.1);
//
//        lupa.move(100,0,seconds(3)).execute();


       // Animation.unstrokeAndUnFill(carpet,seconds(2)).execute();


        carpet.subGroup(1).move(-100,-100).andThen( carpet.subGroup(1).move(100,100)).executeInBackGround();
        wait(seconds(0.3)).execute();
        carpet.subGroup(2).move(0,-100).andThen( carpet.subGroup(2).move(0,100)).executeInBackGround();
        wait(seconds(0.3)).execute();
        carpet.subGroup(3).move(100,-100).andThen( carpet.subGroup(3).move(-100,100)).executeInBackGround();
        wait(seconds(0.3)).execute();
        carpet.subGroup(4).move(100,0).andThen( carpet.subGroup(4).move(-100,0)).executeInBackGround();
        wait(seconds(0.3)).execute();
        carpet.subGroup(5).move(100,100).andThen( carpet.subGroup(5).move(-100,-100)).executeInBackGround();
        wait(seconds(0.3)).execute();
        carpet.subGroup(6).move(0,100).andThen( carpet.subGroup(6).move(0,-100)).executeInBackGround();
        wait(seconds(0.3)).execute();
        carpet.subGroup(7).move(-100,100).andThen( carpet.subGroup(7).move(100,-100)).executeInBackGround();
        wait(seconds(0.3)).execute();
        carpet.subGroup(8).move(-100,0).andThen( carpet.subGroup(8).move(100,0)).executeInBackGround();

        joinBackGroundTasks();




        var carpetBrace= HorizontalBrace.embrace(carpet, TOP,30);

        add(carpetBrace);

        Animation.strokeAndFill(carpetBrace).execute();

        var t3= new StringGobject("L",Fonts.JETBRAINS_MONO.deriveFont(30f),carpetBrace.getBorders().midPoint().plus(0,-10),Color.white);

        add(t3);

        Animation.strokeAndFill(t3).execute();


        var meio=carpet.subGroup(0);
        carpet.subGroupExept(0).changeColor(new Color(255,255,255,50)).execute();

        var meioBrace= HorizontalBrace.embrace(meio, TOP,30);

        add(meioBrace);

        Animation.strokeAndFill(meioBrace).execute();

        var tl3= meioBrace.caption("L/3", TOP);

        add(tl3);

        Animation.strokeAndFill(tl3).execute();






//
//        carpet.move(400,0).executeInBackGround();
//
//        wait(seconds(0.2)).execute();
//
//        var arrow = new SVGGobject("C:\\Users\\PICHAU\\Desktop\\video-mandel\\arrow.svg");
//        add(arrow);
//        arrow.setColor(Color.white);
//        arrow.setPositionTo(Location.at(800,540));
//
//        Animation.strokeAndFill(arrow).executeInBackGround();
//
//        var txt = new CodeBlock(Location.at(200, 480), 20, 400);
//
//        add(txt);
//        txt.newLine("void tapete(){");
//        txt.newLine(" ");
//        txt.newLine("// aqui vira nosso codigo");
//        txt.newLine("// foo();");
//        txt.newLine(" ");
//        txt.newLine("}");
//
//        add(txt);
//        Animation.strokeAndFill(txt).execute();






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


        return mainRectTask.andThen(chieldTask).andThen(wait(10 + (int) (Math.random() * 30))).andThen(() -> Animation.fadeoutGrow(r, 20));

    }

    private Group sierpinskyGobject(Location l1, double width, int depth) {


        var newWidth = width / 3;

        var group = new Group();
        Color color = new Color(255, 255, 255);
                //Color.getHSBColor(0.5f + (float) ((l1.getX() - l1.getY()) / 8000.0), 1, 1);
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
