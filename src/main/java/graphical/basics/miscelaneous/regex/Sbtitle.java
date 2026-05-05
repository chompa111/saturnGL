package graphical.basics.miscelaneous.regex;

import codec.engine.EngineType;
import graphical.basics.behaviors.FollowBehavior;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.SupplierText;
import graphical.basics.gobject.latex.Latex;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class Sbtitle extends Animation {

    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildAnimation() {
        var circle = CircleBuilder.aCircle().build();
        add(circle);
        var sb = new SupplierText(()->"("+(int)circle.getMidPoint().getX()+","+
                (int)circle.getMidPoint().getY()+")"
                , Location.at(0,0), Color.orange);

        add(sb);

        circle.addBehavior(FollowBehavior.asSubtitleWithDelay(circle,sb,10,0.5));

        var sb2 = Latex.of("sbsb");
        add(sb2);

        sb.addBehavior(FollowBehavior.asSubtitleWithDelay(sb,sb2,10,0.5));

        circle.move(300,0).forSeconds(1)
                .andThen(circle.move(0,300).forSeconds(1))
                .andThen(circle.move(-300,0).forSeconds(1))
                .andThen(circle.move(0,-300).forSeconds(10))
                .repeat(5)
                .execute();




    }

    public static void main(String[] args) {
        new Sbtitle().buildAnimation();
    }
}
