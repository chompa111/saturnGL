package graphical.basics.examples;

import codec.engine.EngineType;
import graphical.basics.gobject.TextGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.WaitTask;

import java.awt.*;

public class Baskara extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
        presentationConfig.setHeight(1080);
        presentationConfig.setWidth(1920);
        presentationConfig.setFramerate(60);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    public void buildPresentation() {
    //    getBackGround().getColors().get(0).setColor(new Color(120, 20, 20));
        var initFormula = new TextGobject("ax^2+bx+c=0", new Point(100, 100), Color.white);

        add(initFormula);
        Animation.strokeAndFill(initFormula, seconds(1)).execute();

        var overA = new TextGobject("\\frac{ax^2}{a}+\\frac{bx}{a}+\\frac{c}{a}=\\frac{0}{a}", new Point(100, 100), Color.white);
        var overAExc = overA.subGroupExept(0, 1, 2, 5, 6, 7, 10, 11, 14, 15);
        Positioning.alignAll(initFormula.getGobjects(), overA.subGroup(0, 1, 2, 5, 6, 7, 10, 11, 14, 15).getGobjects())
                .parallel(new WaitTask(seconds(0.5)).andThen(
                        new ContextSetupTask(() -> {
                            add(overAExc);
                            return Animation.strokeAndFill(overAExc, seconds(1));
                        })

                ))
                .execute();

        remove(overAExc);
        remove(initFormula);
        add(overA);

        //simplifying

        var overASiple = new TextGobject("x^2+\\frac{bx}{a}+\\frac{c}{a}=0", new Point(100, 100), Color.white);
        var x = overA.subGroup(1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        var xx = overA.subGroupExept(1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        xx.changeColor(new Color(0, 0, 0, 0)).execute();
        Positioning.alignAll(x.getGobjects(), overASiple.getGobjects()).execute();

        remove(overA);
        add(overASiple);

        new WaitTask(seconds(1)).execute();

        // LatexGobject.indexsize(overASiple);

        // send c/a to the other side

        var coverAright = new TextGobject("x^2+\\frac{bx}{a}=-\\frac{c}{a}", new Point(100, 100), Color.white);
        //add(coverAright);
        // LatexGobject.indexsize(coverAright);

        var subOvserASimple = overASiple.subGroup(0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11);
        var subOvserASimpleExp = overASiple.subGroupExept(0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11);

        var reordered = coverAright.subGroup(0, 1, 2, 3, 4, 5, 6, 9, 10, 11, 7);
        //  LatexGobject.colorizeOrder(subOvserASimple.getGobjects(),reordered.getGobjects());
        var minus = coverAright.subGroup(8);
        Positioning.alignAll(subOvserASimple.getGobjects(), reordered.getGobjects())
                .parallel(subOvserASimpleExp.changeColor(new Color(0, 0, 0, 0)))
                .parallel(Animation.t3b1b(overASiple.subGroup(7), minus, seconds(1)))
                .execute();

        remove(overASiple);
        remove(minus);
        add(coverAright);
        new WaitTask(seconds(1)).execute();

        var step3 = new TextGobject("x^2+\\frac{bx}{a}+(\\frac{b}{2a})^2=-\\frac{c}{a}+(\\frac{b}{2a})^2", new Point(20, 100), Color.white);
//        add(step3);
//        LatexGobject.indexsize(step3);
        var partial = step3.subGroup(0, 1, 2, 3, 4, 5, 6, 15, 16, 17, 18, 19);
        var toadd = step3.subGroupExept(0, 1, 2, 3, 4, 5, 6, 15, 16, 17, 18, 19);
        Positioning.alignAll(coverAright.getGobjects(), partial.getGobjects()).execute();
        add(toadd);
        Animation.strokeAndFill(toadd, seconds(1)).execute();

        remove(coverAright);
        remove(toadd);
        add(step3);
        // LatexGobject.indexsize(step3);

        var step4 = new TextGobject("(x+\\frac{b}{2a})^2=-\\frac{c}{a}+(\\frac{b}{2a})^2", new Point(20, 100), Color.white);
//        add(step4);
//        LatexGobject.indexsize(step4);

        var step3M = step3.subGroup(0, 9, 10, 11, 12, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
        var step3MExp = step3.subGroupExept(0, 9, 10, 11, 12, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);

        var step4M = step4.subGroup(1, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
        var step4MExp = step4.subGroupExept(1, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);

        //LatexGobject.colorizeOrder(step3M.getGobjects(),step4M.getGobjects());

        Positioning.alignAll(step3M.getGobjects(), step4M.getGobjects())
                .parallel(step3MExp.changeColor(new Color(0, 0, 0, 0)))
                .parallel(new WaitTask(seconds(0.5)).andThen(
                        new ContextSetupTask(() -> {
                            add(step4MExp);
                            return Animation.strokeAndFill(step4MExp, seconds(1));
                        })
                ))
                .execute();

        remove(step4MExp);
        remove(step3);
        add(step4);
        // LatexGobject.indexsize(step4);
        new WaitTask(seconds(1)).execute();

        var step5 = new TextGobject("(x+\\frac{b}{2a})^2=-\\frac{c}{a}+\\frac{b^2}{4a^2}", new Point(20, 100), Color.white);
//        add(step5);
//        LatexGobject.indexsize(step5);

        var step4toMove = step4.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19);
        var step4MtoMoveExp = step4.subGroupExept(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19);

        var step5M = step5.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19);


        var four = step5.subGroup(18);
        var squares = step5.subGroup(16, 20);
        Positioning.alignAll(step4toMove.getGobjects(), step5M.getGobjects())
                .parallel(Animation.t3b1b(step4.subGroup(21), squares, seconds(1))
                        .parallel(Animation.t3b1b(step4.subGroup(18), four, seconds(1))))
                .parallel(step4MtoMoveExp.changeColor(new Color(0, 0, 0, 0)))
                .execute();


        remove(four);
        remove(squares);
        remove(step4);
        add(step5);
        // LatexGobject.indexsize(step5);
        new WaitTask(seconds(1)).execute();

        var step6 = new TextGobject("(x+\\frac{b}{2a})^2=\\frac{b^2}{4a^2}-\\frac{c}{a}", new Point(20, 100), Color.white);
//        add(step6);
//        LatexGobject.indexsize(step6);


        Positioning.alignAll(step5.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20).getGobjects(), step6.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 10, 11, 12, 13, 14, 15).getGobjects())
                .parallel(step5.subGroup(14).changeColor(new Color(0, 0, 0, 0)))
                .execute();

        remove(step5);
        add(step6);
        //LatexGobject.indexsize(step6);
        new WaitTask(seconds(1)).execute();


        var step7 = new TextGobject("(x+\\frac{b}{2a})^2=\\frac{b^2-4ac}{4a^2}", new Point(20, 100), Color.white);
//        add(step7);
//        LatexGobject.indexsize(step7);

        //LatexGobject.indexsize(overA);
        //add(overA);

        var step6M = step6.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17);
        var step7M = step7.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 17, 18, 19, 12, 15);
        var longbarr = step7.subGroup(16);
        var a = step7.subGroup(13, 14);
        Positioning.alignAll(step6M.getGobjects(), step7M.getGobjects())
                .parallel(Animation.t3b1b(step6.subGroup(12),longbarr,seconds(1)))
                .parallel(step6.subGroup(12).changeColor(new Color(0, 0, 0, 0)))
                .parallel(Animation.t3b1b(step6.subGroup(13, 14),a,seconds(1)))
                .parallel(step6.subGroup(18, 19).changeColor(new Color(0, 0, 0, 0)))
                .execute();


        remove(a);
        remove(longbarr);
        remove(step6);
        add(step7);
        // LatexGobject.indexsize(step7);
        new WaitTask(seconds(1)).execute();

        var step8 = new TextGobject("x+\\frac{b}{2a}=\\pm\\sqrt{\\frac{b^2-4ac}{4a^2}}", new Point(20, 100), Color.white);
//        add(step8);
//        LatexGobject.indexsize(step8);


        var step7toMove = step7.subGroup(1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        var step7toMoveExp = step7.subGroupExept(1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        var step8toM = step8.subGroup(0, 1, 2, 3, 4, 5, 6, 9 + 1, 10 + 1, 11 + 1, 12 + 1, 13 + 1, 14 + 1, 15 + 1, 16 + 1, 17 + 1, 18 + 1);


        var root = step8.subGroup(7, 8, 9);
        Positioning.alignAll(step7toMove.getGobjects(), step8toM.getGobjects())
                .parallel(Animation.t3b1b(step7.subGroup(8),root,seconds(1)))
                .parallel(step7toMoveExp.changeColor(new Color(0, 0, 0, 0)))
                .execute();

        remove(root);
        remove(step7);
        add(step8);

        new WaitTask(seconds(1)).execute();

        //  LatexGobject.indexsize(step8);

        var step9 = new TextGobject("x+\\frac{b}{2a}=\\pm{\\frac{\\sqrt{b^2-4ac}}{\\sqrt{4a^2}}", new Point(20, 100), Color.white);
//        add(step9);
//        LatexGobject.indexsize(step9);

        var step8M = step8.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 17, 18, 19);
        var step8MExp = step8.subGroupExept(0, 1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 17, 18, 19);
        var step9M = step9.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 19, 20, 21);

        var root8 = step8.subGroup(8, 9);

        var root91 = step9.subGroup(8, 9);

        var root92 = step9.subGroup(17, 18);

        var bar = step9.subGroup(16);


        Positioning.alignAll(step8M.getGobjects(), step9M.getGobjects())
                .parallel(Animation.t3b1b(root8,root91,seconds(1)))
                .parallel(Animation.t3b1b(root8,root92,seconds(1)))
                .parallel(Animation.t3b1b(step8.subGroup(16),bar,seconds(1)))
                .parallel(step8MExp.changeColor(new Color(0, 0, 0, 0)))
                .execute();

        remove(root91);
        remove(root92);
        remove(bar);
        remove(step8);
        add(step9);

        new WaitTask(seconds(1)).execute();

        // LatexGobject.indexsize(step9);

        var step10 = new TextGobject("x+\\frac{b}{2a}=\\pm{\\frac{\\sqrt{b^2-4ac}}{2a}", new Point(20, 100), Color.white);
//        add(step10);
//        LatexGobject.indexsize(step10);

        var step9tomove = step9.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 20);
        var step9tomoveExp = step9.subGroupExept(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 20);
        var step10M = step10.subGroup(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 18);
        var barr2 = step10.subGroup(16);
        var two = step10.subGroup(17);
        //TODO faltou a barra

        Positioning.alignAll(step9tomove.getGobjects(), step10M.getGobjects())
                .parallel(Animation.t3b1b(step9.subGroup(19, 21),two,seconds(1)))
                .parallel(Animation.t3b1b(step9.subGroup(16),barr2,seconds(1)))
                .parallel(step9tomoveExp.changeColor(new Color(0, 0, 0, 0)))
                .execute();

        remove(barr2);
        remove(two);
        remove(step9);
        add(step10);

        new WaitTask(seconds(1)).execute();
        //LatexGobject.indexsize(step10);

        var step11 = new TextGobject("x=\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}", new Point(20, 100), Color.white);
//        add(step11);
//        LatexGobject.indexsize(step11);


        var step10Mtomove = step10.subGroup(0, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18);
        var step10MtomoveExcpt = step10.subGroupExept(0, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18);


        var step11M = step11.subGroup(0, 3, 14, 15, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15);
        var barr3 = step11.subGroup(13);
        var minus2 = step11.subGroup(2);

        Positioning.alignAll(step10Mtomove.getGobjects(), step11M.getGobjects())
                .parallel(Animation.t3b1b(step10.subGroup(1),minus2,seconds(1)))
                .parallel(Animation.t3b1b(step10.subGroup(16),barr3,seconds(1)))
                .parallel(step10MtomoveExcpt.changeColor(new Color(0, 0, 0, 0)))
                .execute();


        new WaitTask(1).execute();
        cut();
    }


    public static void main(String[] args) {
        new Baskara().buildPresentation();
    }
}
