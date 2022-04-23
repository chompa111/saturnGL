package graphical.basics.animations.mandelbrotset;

import codec.CodecType;
import codec.engine.EngineType;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.*;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MBPresentation extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
     //   presentationConfig.setDisableCodec(true);
        //presentationConfig.setDisablePreview(true);
        presentationConfig.setCodec(CodecType.XUGGLE);
        presentationConfig.setEngine(EngineType.JAVAFX);
        presentationConfig.setWidth(1920);
        presentationConfig.setHeight(1080);
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void buildPresentation() {




       // wait(seconds(10)).execute();

        //   switchOff();

        // a primeira coisa que voce precisa saber é que o conjunto de mandelbrot acontece nos numeros complexos

        // vamos revisitar alguns conceitos

        var xaxis = new Line(Location.at(0, 0), Location.at(830, 0), Color.white);
        xaxis.getStrokeThickness().setValue(2.5);
        xaxis.setPositionTo(Location.at(1920 / 2, 1080 / 2));
        add(xaxis);
        Animation.strokeAndFill(xaxis).execute();


        var mid = xaxis.getBorders().midPoint();

        var numbers = new Group();

        var listAux = new ArrayList<Gobject>();

        var zero = new TextGobject("" + 0, mid.plus(-20, 10), Color.white);
        zero.getScale().setValue(0.80);

        for (int i = 1; i < 6; i++) {
            var positive = new TextGobject("" + i, mid.plus((i * 80) - 20, 10), Color.white);
            positive.getScale().setValue(0.80);
            var positiveR = new Line(mid.plus(i * 80, -10), mid.plus(i * 80, 10), Color.white);
//            add(positiveR);
//            add(positive);

            listAux.add(positive);
            listAux.add(positiveR);

            var negative = new TextGobject("-" + i, mid.plus((i * -80) - 50, 10), Color.white);
            negative.getScale().setValue(0.80);
            var negativeR = new Line(mid.plus(i * -80, -10), mid.plus(i * -80, 10), Color.white);
//            add(negative);
//            add(negativeR);

            listAux.add(negative);
            listAux.add(negativeR);

        }






        var group = new Group(listAux);

        add(group);
        add(zero);
        Animation.strokeAndFill(zero).executeInBackGround();
        Animation.strokeAndFill(group).execute();

        // posicionando numeros

        //cut();


        var circle = CircleBuilder.aCircle()
                .withCenter(mid.plus(80, 0))
                .withRadius(13)
                .withColor(new Color(200, 0, 50))
                .build();


        add(circle);
        Animation.fadeInGrow(circle, seconds(1)).executeInBackGround();

        var point = mid.plus(40, -80);
        var xaux = point.getX();
        var realOnLine = new SupplierText(() -> df.format(1 + (point.getX() - xaux) / 80).replace(",", ".") + "", point, new Color(200, 0, 50));
        add(realOnLine);
        realOnLine.getScale().setValue(0.75);
        Animation.fadeInGrow(realOnLine, seconds(1)).execute();

        //cut();

        point.move(60, 0, seconds(2)).executeInBackGround();
        circle.move(60, 0, seconds(2)).execute();

        wait(seconds(2)).execute();

        point.move(-320, 0, seconds(3)).executeInBackGround();
        circle.move(-320, 0, seconds(3)).execute();

        //cut();


        wait(seconds(2)).execute();

        point.move(71, 0, seconds(1)).executeInBackGround();
        circle.move(71, 0, seconds(1)).execute();

        wait(seconds(2)).execute();

        point.move(265, 0, seconds(2)).executeInBackGround();
        circle.move(265, 0, seconds(2)).execute();

        //cut();


        // e se tivessemos mais um eixo

        // switchOn();

        var yaxis = new Line(Location.at(0, 0), Location.at(0, 830), Color.white);
        yaxis.getStrokeThickness().setValue(2.5);
        yaxis.setPositionTo(Location.at(1920 / 2, 1080 / 2));
        add(yaxis);

        Animation.fadeOut(zero).executeInBackGround();
        Animation.strokeAndFill(yaxis).execute();


        listAux = new ArrayList<>();


        for (int i = 1; i < 6; i++) {
            var positive = new TextGobject("-" + i + "i", mid.plus(-85 - 20, (i * 80) - 25), Color.white);
            positive.getScale().setValue(0.80);
            var positiveR = new Line(mid.plus(-10, i * 80), mid.plus(10, i * 80), Color.white);
//            add(positiveR);
//            add(positive);

            listAux.add(positive);
            listAux.add(positiveR);


            var negative = new TextGobject("" + i + "i", mid.plus(-50 - 20, (i * -80) - 25), Color.white);
            negative.getScale().setValue(0.80);
            var negativeR = new Line(mid.plus(-10, i * -80), mid.plus(10, i * -80), Color.white);
//            add(negative);
//            add(negativeR);

            listAux.add(negative);
            listAux.add(negativeR);

        }

        var group2 = new Group(listAux);
        add(group2);


        Animation.strokeAndFill(group2).execute();

        //cut();


        wait(seconds(2)).execute();

        point.move(0, -216, seconds(2)).executeInBackGround();
        circle.move(0, -216, seconds(2)).execute();

        switchOn();

        var point2 = mid.plus(40, -80);
        var yaux2 = point2.getY();
        var realOnLine2 = new SupplierText(() ->
                df.format(1 + (point2.getX() - xaux) / 80).replace(",", ".") + ((0 - (point2.getY() - yaux2) / 80) > 0 ? "+" : "")
                        + df.format(0 - (point2.getY() - yaux2) / 80).replace(",", ".") + "i", point2, new Color(200, 0, 50));


        point2.setX(point.getX());
        point2.setY(point.getY());
        add(realOnLine2);
        Animation.fadeIn(realOnLine2, seconds(2)).execute();

        remove(realOnLine);

        point2.move(-102, -45, seconds(2)).executeInBackGround();
        circle.move(-102, -45, seconds(2)).execute();

        wait(seconds(2)).execute();

        //cut();

        point2.move(102, 385, seconds(2)).executeInBackGround();
        circle.move(102, 385, seconds(2)).execute();

//
//        var yaxis= new Line(Location.at(100,100),Location.at(100,500+200), Color.white);
//        yaxis.getStrokeThickness().setValue(2.5);
//
//
//        var xaxis= new Line(Location.at(50,450+200),Location.at(450+200,450+200), Color.white);
//        xaxis.getStrokeThickness().setValue(2.5);
//
//        add(yaxis);
//        add(xaxis);
//
//        Animation.strokeAndFill(xaxis)
//                .parallel( Animation.strokeAndFill(yaxis))
//                .execute();
//
//
//        var real=new TextGobject("\\mathbb{R}",Location.at(450-20+200,450+200),Color.white);
//        add(real);
//
//        var imaginary=new TextGobject("\\mathbb{I}",Location.at(100+50,100),Color.white);
//        add(imaginary);
//
//        Animation.strokeAndFill(real)
//                .parallel(Animation.strokeAndFill(imaginary))
//                .execute();
//


    }

    public static void main(String[] args) {
        new MBPresentation().build();
    }
}
