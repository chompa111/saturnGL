package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class CircleEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {


//        var svg=new SVGGobject("C:\\Users\\PICHAU\\Desktop\\azuis.svg");
//        var balao=svg.getGroup("balao").toGroupGobject();
       // add(balao);

        var circle= CircleBuilder.aCircle().build();

        var rect = new Rect(new Point(200,200), new Point(300,300),Color.red);

        var quatro=new LatexGobject(new Font("Dialog",Font.PLAIN,350),"4",new Point(500,500),Color.orange);
        var sh=quatro.getGobjects().get(0);

        //add(balao);

        Animation.t3b1b(rect,circle,seconds(100)).execute();


    }

    public static void main(String[] args) {
        new CircleEx().buildPresentation();
    }
}
