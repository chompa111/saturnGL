package graphical.basics.miscelaneous;

import graphical.basics.ColorHolder;
import graphical.basics.behaviors.FollowBehavior;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.StringGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

public class Behave extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
    }

    @Override
    protected void buildAnimation() {

        for (int i=0;i<100;i++){
            var bolinha = CircleBuilder.aCircle()
                    .withColor(ColorHolder.randomColor())
                    .withRadius(10).build();
            add(bolinha);
            var txt = new StringGobject(bolinha.getMidPoint().toString());
            txt.setPositionTo(Location.at(500, 540));
            add(txt);
            txt.addBehavior(FollowBehavior.asSubtitle(bolinha, txt, 10));
            txt.addBehavior(() -> txt.set(bolinha.getMidPoint().toString()));


            bolinha.move(0, 0, 1)
                    .andThen(() -> bolinha.move(-50 + Math.random() * 100, -50 + Math.random() * 100).forSeconds(0.2))
                    .repeat(1000)
                    .executeInBackGround();
        }


    }

    public static void main(String[] args) {
        new Behave().build();
    }
}
