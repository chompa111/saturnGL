package graphical.basics.examples;

import codec.engine.EngineType;
import graphical.basics.gobject.*;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

import static graphical.basics.gobject.JavaHilighter.INTELLIJ_GRAY;

public class TextEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        // presentationConfig.setDisableCodec(true);
        presentationConfig.setEngine(EngineType.JAVAFX);
        //  presentationConfig.setFramerate(75);
    }

    @Override
    protected void buildPresentation() {

        //fibo(3, 0);


        var txt = new CodeBlock(Location.at(200, 200), 30, 600);
        add(txt);
        txt.newLine("int fibo(int n){");
        txt.newLine("   if(n==0) return 1;");
        txt.newLine("   return fibo(n-1)+fibo(n-2);");
        txt.newLine("var txt = new CodeBlock(Location.at(200, 200), 30, 600);");
        txt.newLine("}");


        wait(seconds(10)).execute();
        Animation.unstrokeAndUnFill(txt, seconds(4)).execute();

//
//        var lens = new Magnifier(Location.at(250,250), Location.at(350,350));
//        lens.move(0,-100).execute();
//        add(lens);
//        lens.getScale().change(1,seconds(2)).execute();
//
//        lens.move(170,0,seconds(2)).execute();
//        lens.getScale().change(3,seconds(3)).execute();


        var brace = HorizontalBrace.embrace(txt.getBackground(), HorizontalBrace.Placement.DOWN);
        add(brace);

        Animation.strokeAndFill(brace).executeInBackGround();

        //  Animation.fadeInGrow(brace,seconds(1)).executeInBackGround();

        // brace.expandCenterAnimated(200,seconds(1)).execute();

        var t3 = new StringGobject("L/3");
        add(t3);

        t3.setPositionTo(brace.getMidPoint().plus(0, 30));

        Animation.strokeAndFill(t3).execute();

        var brace2 = VerticalBrace.embrace(txt.getBackground(), VerticalBrace.Placement.LEFT);
        add(brace2);
        Animation.strokeAndFill(brace2).execute();


        StringGobject batata = new StringGobject("batata");
        add(batata);
        batata.setPositionTo(brace2.getMidPoint().plus(-100,0));

        // lens.move(100,0,seconds(3)).execute();


//        txt.addDebugLine();
//        Animation.strokeAndFill(txt.getDebbugLine()).execute();
//
//        Animation.fadeInGrow(txt.getDebbugLine(),seconds(1)).execute();

        // Animation.strokeAndFill(txt, seconds(1)).execute();

//        txt.newLineAnimated(2,"// bman me ajuda").execute();
//        txt.getText().getLine(2).typeEffect().execute();

        //txt.getText().typingEffect().execute();

//
//        txt.addDebugLine();
//        Animation.strokeAndFill(txt.getDebbugLine()).execute();
//        wait(seconds(6)).execute();
//        txt.debuggNextLineAnimated(seconds(1)).execute();
//        wait(seconds(6)).execute();
//
//
//        var txt2 = new CodeBlock(Location.at(200, 200).plus(50, 50), 30, 600);
//        add(txt2);
//        txt2.newLine("int fibo(int n){");
//        txt2.newLine("   if(n==0) return 1;");
//        txt2.newLine("   return fibo(n-1)+fibo(n-2);");
//        txt2.newLine("}");
//
//        Animation.strokeAndFill(txt2, seconds(1)).execute();
//
//
//        wait(seconds(2)).execute();
//
//        Animation.fadeOut(txt2).execute();


//        // switchOff();
//
//        var txt = new Text(INTELLIJ_GRAY, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(100, 500));
//
//        add(txt);
//        txt.newLine("int fibo(int n){");
//        txt.newLine("   if(n==0) return 1");
//        txt.newLine("   return fibo(n-1)+fibo(n-2);");
//        txt.newLine("}");
//
//        new JavaHilighter().colorize(txt);
//
//        var rr = new Rect(txt.getBorders().getL1().plus(-50, -50), txt.getBorders().getL2().plus(50, 50), JavaHilighter.INTELLIJ_BACKGROUND);
//        addBefore(txt, rr);
//
//        Animation.strokeAndFill(rr).executeInBackGround();
//        txt.typeEffect().execute();
//        wait(seconds(2)).execute();
//
//        var debugline = new Rect(txt.getBorders().getL1().plus(-50, 0), txt.getBorders().getL1().plus(txt.getBorders().getL2().getX() - 50, 30 * 1.15), JavaHilighter.INTELLIJ_DEBUGGER_COLOR);
//        addBefore(txt, debugline);
//
//        debugline.move(0, 30 * 1.15, seconds(0.5) + 1).execute();
//        wait(seconds(1)).execute();
//        debugline.move(0, 30 * 1.15, seconds(0.5) + 1).execute();
//        wait(seconds(1)).execute();
//
////        var rr2=new Rect(txt.getBorders().getL1().plus(-50,-50),txt.getBorders().getL2().plus(50,50),new Color(0,0,0,120));
////        add(rr2);
////        Animation.fadeIn(rr2).execute();
//
//        batatao(50, 3);
//
//        batatao(100, 2);
//
//        batatao(150, 1);
//
//        batatao(200, 0);
//
//
////        txt.newLineAnimated(1, "    // batata digitada").execute();
////        new JavaHilighter().colorize(txt);
////        txt.getLine(1).typeEffect().execute();
////        txt.newLineAnimated(1, "    // batata digitada").execute();
////        new JavaHilighter().colorize(txt);
////        txt.getLine(1).typeEffect().execute();
////        txt.newLineAnimated(1, "    // batata digitada").execute();
////        new JavaHilighter().colorize(txt);
////        txt.getLine(1).typeEffect().execute();
////        txt.newLineAnimated(1, "    // batata digitada").execute();
////        new JavaHilighter().colorize(txt);
////        txt.getLine(1).typeEffect().execute();
//
//
////
////        // Animation.strokeAndFill(txt).execute();
////
////        var txt2 = new Text(INTELLIJ_GRAY, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(100, 500));
////
////        //  add(txt2);
////        txt2.newLine("public batata(int sacanagem){");
////        txt2.newLine("  return batata(sacanagem);");
////        txt2.newLine("}");
////
////        new JavaHilighter().colorize(txt2);

    }

    public int fibo(int n, int profund) {

        var txt = new CodeBlock(Location.at(200 + profund * 50, 200 + profund * 50), 30, 700);
        add(txt);
        txt.newLine("int fibo(int n){");
        txt.newLine("   if(n==0 || n==1) return 1;");
        txt.newLine("   return fibo(n-1)+fibo(n-2);");
        txt.newLine("}");

        txt.getTextComment().newLine(0, "  :n=" + n);


        txt.addDebugLine();
        Animation.strokeAndFill(txt, seconds(0.8)).execute();
        // txt.getText().typeEffect().execute();

        // Animation.strokeAndFill(txt.getDebbugLine()).execute();

        txt.debuggNextLineAnimated(seconds(0.5) + 1).execute();
        txt.getTextComment().newLine(1, "       : (" + (n == 0 || n == 1) + ")");
        Animation.fadeInGrow(txt.getTextComment().getLine(1), seconds(1)).execute();
        if (n == 1 || n == 0) {

            Animation.fadeOut(txt).execute();
            remove(txt);
            return 1;
        }

        txt.debuggNextLineAnimated(seconds(0.5) + 1).execute();

        var borders = txt.getText().getLine(2).getFirstSubstring("fibo(n-1)").getBorders();
        txt.getDebbugLine().getP1().sendTo(borders.getL1(), seconds(1)).executeInBackGround();
        txt.getDebbugLine().getP2().sendTo(borders.getL2(), seconds(1)).execute();
        int fibo1 = fibo(n - 1, profund + 1);


        var borders2 = txt.getText().getLine(2).getFirstSubstring("fibo(n-2)").getBorders();
        txt.getDebbugLine().getP1().sendTo(borders2.getL1(), seconds(1)).executeInBackGround();
        txt.getDebbugLine().getP2().sendTo(borders2.getL2(), seconds(1)).execute();


        int fibo2 = fibo(n - 2, profund + 1);

        Animation.fadeOut(txt).execute();
        remove(txt);
        return fibo1 + fibo2;
    }

    ;

    public void batatao(int x, int depth) {

        var txt = new Text(INTELLIJ_GRAY, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(100, 500));


        txt.newLine("int fibo(int n){");
        txt.newLine("   if(n==0) return 1;");
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

        var pepe = txt.getLine(1).putInFront("     : " + depth + " == 0 (" + (depth == 0) + ")", Fonts.JETBRAINS_MONO.deriveFont(Font.ITALIC, 30f), new Color(255, 255, 255, 50));
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
