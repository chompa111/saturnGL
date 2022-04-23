package graphical.basics.examples;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Fonts;
import graphical.basics.gobject.TextGobject;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class ExStroke extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(false);
    }

    @Override
    protected void buildPresentation() {
        var hello= new TextGobject(Fonts.COMPUTER_MODERN_I.deriveFont(50f),"Hello World",Location.at(500,500),Color.red);
        add(hello);


        var circle = CircleBuilder.aCircle().build();
        circle.setStrokeThickness(new DoubleHolder(3));
   //     circle.setStrokeColorHolder(new ColorHolder(Color.red));
        add(circle);
        Animation.strokeAndFill(circle).execute();

        circle.getStrokeThickness().change(5,seconds(1)).execute();


        Rect r= new Rect(Location.at(100,100), Location.at(200,200),Color.orange);

        Positioning.align(r,circle, Positioning.Reference.CENTER);
        r.getAngle().setValue(Math.toRadians(90));

        r.setStrokeColorHolder(new ColorHolder(Color.green));
        r.setStrokeThickness(new DoubleHolder(3));

       var sh=(ShapeGobject2) new TextGobject("A", Location.at(500,500),Color.white).getGobjects().get(0);
      // sh.setStrokeColorHolder(new ColorHolder(Color.blue));


        //add(r);

        var circle2 = CircleBuilder.aCircle().withRadius(400).withColor(Color.magenta).build();
        circle2.setStrokeColorHolder(new ColorHolder(Color.blue));

        Animation.t3b1b(circle,sh,seconds(1)).execute();
    }

    public static void main(String[] args) {
        new ExStroke().build();
    }
}
