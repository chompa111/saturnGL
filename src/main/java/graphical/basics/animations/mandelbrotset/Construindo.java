package graphical.basics.animations.mandelbrotset;

import codec.engine.EngineType;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.Fonts;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.Line;
import graphical.basics.gobject.Text;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;
import java.util.List;

public class Construindo extends Presentation {


    Text txt;

    public static Location MID = Location.at(1920.0 / 2, 1080.0 / 2);
    public static Location ORIGIN = Location.at(0, 0);

    @Override
    public void setup(PresentationConfig presentationConfig) {
      //  presentationConfig.setDisableCodec(true);
        presentationConfig.setWidth(1920);
        presentationConfig.setHeight(1080);
       // presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildPresentation() {

        //switchOff();

        var rect = new Rect(Location.at(500, 500), Location.at(1200, 1200), Color.black);

        rect.setPositionTo(MID);
        rect.setStrokeColorHolder(new ColorHolder(Color.white));

        add(rect);

        Animation.strokeAndFill(rect, seconds(1)).execute();
        cut();
        rect.move(300, 0).execute();
        cut();

        txt = new Text(Color.white, Fonts.JETBRAINS_MONO.deriveFont(40f), Location.at(50, 300));

        add(txt);


        txt.newLine(" Tres Passos :");
        Animation.strokeAndFill(txt.getLine(0)).execute();

        cut();

        txt.newLine(" ");
        txt.newLine("   1) pegue a area disponivel ");
        txt.newLine("     divida em 9 quadrados iguais.");
        Animation.strokeAndFill(txt.getLine(2)).executeInBackGround();
        Animation.strokeAndFill(txt.getLine(3)).execute();


        cut();

        drawLines(rect);
        wait(seconds(1)).execute();

        cut();
        txt.newLine(" ");
        txt.newLine("   2) pinte o quadrado do meio");

        Animation.strokeAndFill(txt.getLine(5)).execute();

        cut();
        var quadrados = makeSquares(rect);
        add(quadrados);

        quadrados.getGobjects().get(0).changeColor(Color.white).execute();

        cut();
        txt.newLine(" ");
        txt.newLine("   3) repita os passos para cada um");
        txt.newLine("      dos quadrados restantes ");

        Animation.strokeAndFill(txt.getLine(7)).executeInBackGround();
        Animation.strokeAndFill(txt.getLine(8)).execute();
        cut();

        quadrados.subGroup(1, 2, 3, 4, 5, 6, 7, 8).onChildren(g -> g.changeColor(new Color(203, 170, 36, 169), 20).andThen(g.changeColor(new Color(0, 0, 0, 0), 20)), 5).execute();

        cut();
        /// vamos escolher primeiro o quadrado do canto esquerdo
        quadrados.getGobjects().get(1).changeColor(new Color(36, 150, 203, 70)).execute();

        cut();
        txt.getLine(2).changeColor(Color.orange).executeInBackGround();
        txt.getLine(3).changeColor(Color.orange).execute();


        drawLines((Rect) quadrados.getGobjects().get(1));
        wait(seconds(1)).execute();
        cut();

        ///


        txt.getLine(2).changeColor(Color.WHITE).executeInBackGround();
        txt.getLine(3).changeColor(Color.WHITE).executeInBackGround();

        wait(seconds(1)).execute();

        txt.getLine(5).changeColor(Color.orange).execute();

        var quadrados2 = makeSquares((Rect) quadrados.getGobjects().get(1));

        add(quadrados2);

        quadrados2.getGobjects().get(0).changeColor(Color.white).execute();

        selecionaFraseTres();

        quadrados2.subGroup(1, 2, 3, 4, 5, 6, 7, 8).onChildren(g -> g.changeColor(new Color(203, 170, 36, 169), 20).andThen(g.changeColor(new Color(0, 0, 0, 0), 20)), 5).execute();

        quadrados.getGobjects().get(1).changeColor(new Color(0,0,0,0)).execute();
        quadrados2.getGobjects().get(1).changeColor(new Color(36, 150, 203, 70)).execute();


        selecionaFraseUm();

        drawLines((Rect) quadrados2.getGobjects().get(1));

        selecionaFraseDois();

        var quadrados3 = makeSquares((Rect) quadrados2.getGobjects().get(1));
        add(quadrados3);

        quadrados3.getGobjects().get(0).changeColor(Color.white).execute();

        selecionaFraseTres();

        quadrados3.subGroup(1, 2, 3, 4, 5, 6, 7, 8).onChildren(g -> g.changeColor(new Color(203, 170, 36, 169), 20).andThen(g.changeColor(new Color(0, 0, 0, 0), 20)), 5).execute();


    }


    public void drawLines(Rect r) {
        drawLines(r.getP1(), r.getP2());
    }

    public void drawLines(Location p1, Location p2) {
        var rect = new Rect(p1, p2, Color.white);// just for referential

        var l1 = new Line(rect.getP1().plus(rect.getWidth() / 3, 0), rect.getP1().plus(rect.getWidth() / 3, rect.getHeight()), Color.white);
        add(l1);

        Animation.strokeAndFill(l1).executeInBackGround();
        wait(10).execute();

        var l2 = new Line(rect.getP1().plus(2 * rect.getWidth() / 3, 0), rect.getP1().plus(2 * rect.getWidth() / 3, rect.getHeight()), Color.white);
        add(l2);

        Animation.strokeAndFill(l2).executeInBackGround();
        wait(10).execute();


        var l3 = new Line(rect.getP1().plus(0, rect.getWidth() / 3), rect.getP1().plus(rect.getWidth(), rect.getWidth() / 3), Color.white);
        add(l3);

        Animation.strokeAndFill(l3).executeInBackGround();
        wait(10).execute();


        var l4 = new Line(rect.getP1().plus(0, 2 * rect.getWidth() / 3), rect.getP1().plus(rect.getWidth(), 2 * rect.getWidth() / 3), Color.white);
        add(l4);

        Animation.strokeAndFill(l4).executeInBackGround();
    }

    public Group makeSquares(Rect rect) {
        return makeSquares(rect.getP1(), rect.getP2());
    }

    Group makeSquares(Location p1, Location p2) {
        var quadrados = new Group();
        var rect = new Rect(p1, p2, Color.white);// just for referential

        var q0 = new Rect(rect.getP1().plus(rect.getWidth() / 3, rect.getWidth() / 3), rect.getP1().plus(2 * rect.getWidth() / 3, 2 * rect.getWidth() / 3), new Color(0, 0, 0, 0));

        var q1 = new Rect(rect.getP1().plus(0 * rect.getWidth() / 3, 0 * rect.getWidth() / 3), rect.getP1().plus(1 * rect.getWidth() / 3, 1 * rect.getWidth() / 3), new Color(0, 0, 0, 0));

        var q2 = new Rect(rect.getP1().plus(1 * rect.getWidth() / 3, 0 * rect.getWidth() / 3), rect.getP1().plus(2 * rect.getWidth() / 3, 1 * rect.getWidth() / 3), new Color(0, 0, 0, 0));

        var q3 = new Rect(rect.getP1().plus(2 * rect.getWidth() / 3, 0 * rect.getWidth() / 3), rect.getP1().plus(3 * rect.getWidth() / 3, 1 * rect.getWidth() / 3), new Color(0, 0, 0, 0));

        var q4 = new Rect(rect.getP1().plus(2 * rect.getWidth() / 3, 1 * rect.getWidth() / 3), rect.getP1().plus(3 * rect.getWidth() / 3, 2 * rect.getWidth() / 3), new Color(0, 0, 0, 0));

        var q5 = new Rect(rect.getP1().plus(2 * rect.getWidth() / 3, 2 * rect.getWidth() / 3), rect.getP1().plus(3 * rect.getWidth() / 3, 3 * rect.getWidth() / 3), new Color(0, 0, 0, 0));

        var q6 = new Rect(rect.getP1().plus(1 * rect.getWidth() / 3, 2 * rect.getWidth() / 3), rect.getP1().plus(2 * rect.getWidth() / 3, 3 * rect.getWidth() / 3), new Color(0, 0, 0, 0));

        var q7 = new Rect(rect.getP1().plus(0 * rect.getWidth() / 3, 2 * rect.getWidth() / 3), rect.getP1().plus(1 * rect.getWidth() / 3, 3 * rect.getWidth() / 3), new Color(0, 0, 0, 0));

        var q8 = new Rect(rect.getP1().plus(0 * rect.getWidth() / 3, 1 * rect.getWidth() / 3), rect.getP1().plus(1 * rect.getWidth() / 3, 2 * rect.getWidth() / 3), new Color(0, 0, 0, 0));

        quadrados.addAll(List.of(q0, q1, q2, q3, q4, q5, q6, q7, q8));
        return quadrados;
    }


    void selecionaFraseUm(){
        txt.getLine(2).changeColor(Color.white).executeInBackGround();
        txt.getLine(3).changeColor(Color.white).executeInBackGround();

        txt.getLine(5).changeColor(Color.white).executeInBackGround();

        txt.getLine(7).changeColor(Color.white).executeInBackGround();
        txt.getLine(8).changeColor(Color.white).executeInBackGround();

        wait(seconds(0.5)).execute();

        txt.getLine(2).changeColor(Color.orange).executeInBackGround();
        txt.getLine(3).changeColor(Color.orange).executeInBackGround();

    }

    void selecionaFraseDois(){
        txt.getLine(2).changeColor(Color.white).executeInBackGround();
        txt.getLine(3).changeColor(Color.white).executeInBackGround();

        txt.getLine(5).changeColor(Color.white).executeInBackGround();

        txt.getLine(7).changeColor(Color.white).executeInBackGround();
        txt.getLine(8).changeColor(Color.white).executeInBackGround();

        wait(seconds(0.5)).execute();

        txt.getLine(5).changeColor(Color.orange).executeInBackGround();
    }
    void selecionaFraseTres(){
        txt.getLine(2).changeColor(Color.white).executeInBackGround();
        txt.getLine(3).changeColor(Color.white).executeInBackGround();

        txt.getLine(5).changeColor(Color.white).executeInBackGround();

        txt.getLine(7).changeColor(Color.white).executeInBackGround();
        txt.getLine(8).changeColor(Color.white).executeInBackGround();

        wait(seconds(0.5)).execute();

        txt.getLine(7).changeColor(Color.orange).executeInBackGround();
        txt.getLine(8).changeColor(Color.orange).executeInBackGround();
    }


    public static void main(String[] args) {
        new Construindo().build();
    }
}
