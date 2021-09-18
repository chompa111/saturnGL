package graphical.basics.examples;

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

//        presentationConfig.setDisableCodec(true);
        presentationConfig.setHeight(1080);
        presentationConfig.setWidth(1920);
        presentationConfig.setFramerate(60);
    }

    @Override
    protected void buildPresentation() {
        var rect = new Rect(Location.at(100,100),Location.at(300,300), Color.green);


        add(rect);


        var pencil = new Pencil(rect.getP1(),Color.magenta);
        add(pencil);

        rect.move(200,0).execute();


//        rect.setStrokeColorHolder(new ColorHolder(Color.white));
//
//
//        rect.getAngle().setValue(Math.toRadians(90));
//
//        var circle= CircleBuilder.aCircle().withColor(Color.green).build();
//        rect.setPositionTo(circle.getBorders().midPoint());
//        add(circle);
//        Animation.strokeAndFill(circle,seconds(1)).execute();
//
//        Animation.t3b1b(circle,rect,seconds(1)).execute();
//        var text=new TextGobject("L^2+1",Location.at(500,500),Color.white);
//        wait(seconds(1)).execute();
//        Animation.t3b1b(rect,text,seconds(1)).execute();
//
//        var tratorSvg = new SVGGobject("C:\\Users\\PICHAU\\trato.svg").toGroupGobject();
//        tratorSvg.setPositionTo(Location.at(500,500));
//        wait(seconds(1)).execute();
//        Animation.t3b1b(text,tratorSvg,seconds(1)).execute();
//
//       // rect.transform(text).execute();
//
//        //circle.transform(rect).execute();


    }

    public static void main(String[] args) {
        new Quadrado().build();
    }
}
