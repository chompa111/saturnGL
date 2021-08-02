package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class SVGExample extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {
       // var logo = new LatexGobject(new Font("Dialog",Font.ITALIC,30),"C:\\Users\\PICHAU\\Desktop\\azuis.svg",new Point(500,500),Color.red);

        var logo= CircleBuilder.aCircle().build().asShapeGobject();

        add(logo);
        //logo.getAngle().setValue(3.1415926535/2);
       logo.scale(10).execute();
        logo.move(100,0,seconds(10)).execute();
//
//                logo.rotate(1)
//                .parallel(
//                        logo.scale(5))
//                .repeat(2)
//                .andThen(logo.changeColor(Color.cyan).parallel(logo.move(0,500))
//                        .parallel(logo.rotate(4.14)))
//                .andThen(logo.move(10000,0))
//                .wait(seconds(2))
//                .andThen(logo.move(-10000,0))
//                .andThen(logo.move(-100,0))
//                .andThen(logo.move(100,0))
//                .execute();

//        Animation.strokeAndFill(logo,seconds(3)).execute();
//        Animation.fadeInGrow(logo,seconds(1)).execute();

        cut();
    }

    public static void main(String[] args) {
        new SVGExample().buildPresentation();
    }
}
