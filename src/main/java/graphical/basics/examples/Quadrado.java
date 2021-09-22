package graphical.basics.examples;

import codec.CodecType;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Pencil;
import graphical.basics.gobject.TextGobject;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.value.ChangeType;

import java.awt.*;

public class Quadrado extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

        presentationConfig.setDisableCodec(false);
        presentationConfig.setHeight(1080);
        presentationConfig.setWidth(1920);
        presentationConfig.setFramerate(60);
    }

    @Override
    protected void buildPresentation() {
        var rect = new Rect(Location.at(100,100),Location.at(300,300), Color.red);


        //add(rect);

//
//        var pencil = new Pencil(Location.getTransformedObservedLocation(rect.getP1(),rect),Color.magenta);
//        var pencil2 = new Pencil(Location.getTransformedObservedLocation(rect.getP2(),rect),Color.yellow);
//        add(pencil);
//        add(pencil2);
//
//        rect.move(1000,0,seconds(10))
//                .andThen(rect.move(0,500,seconds(10)))
//                .andThen(rect.move(-1000,0,seconds(10)))
//                .andThen(rect.move(0,-500,seconds(10)))
//                .executeInBackGround();
//        rect.getAngle().change(0.1,seconds(40),ChangeType.CONSTANT_SPEED).execute();


        rect.setStrokeColorHolder(new ColorHolder(Color.white));


        rect.getAngle().setValue(Math.toRadians(90));

        var circle= CircleBuilder.aCircle().withColor(Color.green).build();
        rect.setPositionTo(circle.getBorders().midPoint());
        add(circle);
        Animation.strokeAndFill(circle,seconds(1)).execute();

        Animation.t3b1b(circle,rect,seconds(1)).execute();
        var text=new TextGobject("L^2+1",Location.at(500,500),Color.white);
        wait(seconds(1)).execute();
       rect.transform(text,seconds(1)).execute();
//
//        var tratorSvg = new SVGGobject("C:\\Users\\PICHAU\\trato.svg").toGroupGobject();
//        tratorSvg.setPositionTo(Location.at(500,500));
//        wait(seconds(1)).execute();
//        Animation.t3b1b(text,tratorSvg,seconds(1)).execute();

       // rect.transform(text).execute();

        //circle.transform(rect).execute();


    }

    public static void main(String[] args) {
        new Quadrado().build();
    }
}
