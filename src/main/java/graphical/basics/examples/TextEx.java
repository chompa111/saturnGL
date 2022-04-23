package graphical.basics.examples;

import graphical.basics.gobject.*;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.effects.T3b1b;

import java.awt.*;

import static graphical.basics.gobject.JavaHilighter.INTELLIJ_GRAY;
import static graphical.basics.gobject.JavaHilighter.INTELLIJ_ORANGE;

public class TextEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        //presentationConfig.setDisableCodec(true);
        //  presentationConfig.setFramerate(75);
    }

    @Override
    protected void buildPresentation() {

        // switchOff();

        var txt = new Text(INTELLIJ_GRAY, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(100, 500));

        add(txt);
        txt.newLine("int fibo(int n){");
        txt.newLine("   if(n==0) return 1");
        txt.newLine("   return fibo(n-1)+fibo(n-2);");
        txt.newLine("}");

        new JavaHilighter().colorize(txt);

        var rr = new Rect(txt.getBorders().getL1().plus(-50, -50), txt.getBorders().getL2().plus(50, 50), JavaHilighter.INTELLIJ_BACKGROUND);
        addBefore(txt, rr);

        Animation.strokeAndFill(rr).executeInBackGround();
        txt.typeEffect().execute();
        wait(seconds(2)).execute();

        var debugline = new Rect(txt.getBorders().getL1().plus(-50, 0), txt.getBorders().getL1().plus(txt.getBorders().getL2().getX() - 50, 30 * 1.15), JavaHilighter.INTELLIJ_DEBUGGER_COLOR);
        addBefore(txt, debugline);

        debugline.move(0, 30 * 1.15, seconds(0.5) + 1).execute();
        wait(seconds(1)).execute();
        debugline.move(0, 30 * 1.15, seconds(0.5) + 1).execute();
        wait(seconds(1)).execute();

//        var rr2=new Rect(txt.getBorders().getL1().plus(-50,-50),txt.getBorders().getL2().plus(50,50),new Color(0,0,0,120));
//        add(rr2);
//        Animation.fadeIn(rr2).execute();

        batatao(50, 3);

        batatao(100, 2);

        batatao(150, 1);

        batatao(200, 0);


//        txt.newLineAnimated(1, "    // batata digitada").execute();
//        new JavaHilighter().colorize(txt);
//        txt.getLine(1).typeEffect().execute();
//        txt.newLineAnimated(1, "    // batata digitada").execute();
//        new JavaHilighter().colorize(txt);
//        txt.getLine(1).typeEffect().execute();
//        txt.newLineAnimated(1, "    // batata digitada").execute();
//        new JavaHilighter().colorize(txt);
//        txt.getLine(1).typeEffect().execute();
//        txt.newLineAnimated(1, "    // batata digitada").execute();
//        new JavaHilighter().colorize(txt);
//        txt.getLine(1).typeEffect().execute();


//
//        // Animation.strokeAndFill(txt).execute();
//
//        var txt2 = new Text(INTELLIJ_GRAY, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(100, 500));
//
//        //  add(txt2);
//        txt2.newLine("public batata(int sacanagem){");
//        txt2.newLine("  return batata(sacanagem);");
//        txt2.newLine("}");
//
//        new JavaHilighter().colorize(txt2);

    }

    public void batatao(int x, int depth) {

        var txt = new Text(INTELLIJ_GRAY, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(100, 500));


        txt.newLine("int fibo(int n){");
        txt.newLine("   if(n==0) return 1");
        txt.newLine("   return fibo(n-1)+fibo(n-2);");
        txt.newLine("}");

        txt.changeSetPosition(x, x);

        new JavaHilighter().colorize(txt);

        var rr = new Rect(txt.getBorders().getL1().plus(-50, -50), txt.getBorders().getL2().plus(150, 50), JavaHilighter.INTELLIJ_BACKGROUND);
        add(rr);
        var rr3 = new Rect(txt.getBorders().getL1().plus(-50, -50), txt.getBorders().getL2().plus(150, 50), new Color(0, 0, 40, 80));
        //   add(rr2);
        rr3.changeSetPosition(-17, -17);
        addBefore(rr, rr3);

        Animation.strokeAndFill(rr, seconds(0.5) + 1).executeInBackGround();
        Animation.strokeAndFill(rr3, seconds(0.5) + 1).executeInBackGround();
        //  txt.typeEffect().execute();

        add(txt);
        Animation.fadeIn(txt).execute();

        var commend = new StringGobject("       :n = " + depth, Fonts.JETBRAINS_MONO.deriveFont(Font.ITALIC, 30f), txt.getLine(0).getRef().plus(txt.getLine(0).getBorders().getwidth(), 0), new Color(255, 255, 255, 50));

        add(commend);
        Animation.fadeInGrow(commend, seconds(0.5) + 1).execute();

        wait(seconds(2)).execute();

        var debugline = new Rect(txt.getBorders().getL1().plus(-50, 0), txt.getBorders().getL1().plus(txt.getBorders().getwidth() + 150, 30 * 1.15), JavaHilighter.INTELLIJ_DEBUGGER_COLOR);
        addBefore(txt, debugline);

        debugline.move(0, 30 * 1.15, seconds(0.5) + 1).execute();
        wait(seconds(1)).execute();

        var pepe = txt.getLine(1).putInFront("     : " + depth + " == 0 ("+(depth==0)+")", Fonts.JETBRAINS_MONO.deriveFont(Font.ITALIC, 30f), new Color(255, 255, 255, 50));
        add(pepe);
        Animation.fadeInGrow(pepe, seconds(0.5) + 1).execute();

        debugline.move(0, 30 * 1.15, seconds(0.5) + 1).execute();
        wait(seconds(1)).execute();

//        var removed1 = txt.replaceLine(0, "class Simple {");
//        new JavaHilighter().colorize(txt);
//        var removed2 = txt.replaceLine(0, " ");
//
//        add(removed1);
//        Animation.t3b1b(removed1, removed2, T3b1b.TransformationType.BEST_MATCHING, seconds(1)).execute();
//
//        remove(removed2);
//
//        //switchOn();
//        txt.replaceLine(0, "class Simple {");
//        new JavaHilighter().colorize(txt);
//
//        txt.getLine(0).erase(7).execute();


        //   var rr2=new Rect(txt.getBorders().getL1().plus(-50,-50),txt.getBorders().getL2().plus(50,50),new Color(0,0,0,120));
        //  rr2.changeSetPosition(-10,-10);


        // Animation.fadeIn(rr2).execute();


    }

    public static void main(String[] args) {
        new TextEx().build();

    }


}
