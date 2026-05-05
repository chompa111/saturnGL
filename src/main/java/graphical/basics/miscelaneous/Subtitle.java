package graphical.basics.miscelaneous;

import graphical.basics.behaviors.FollowBehavior;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.StringGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class Subtitle extends Animation {

    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
    }

    @Override
    protected void buildAnimation() {
        var bola = CircleBuilder.aCircle()
                .withColor(Color.red).build();
        add(bola);

        var bola2 = CircleBuilder.aCircle().withCenter(Location.at(200, 200))
                .withColor(Color.blue).build();
        add(bola2);

        var txt = new StringGobject("julian");
        add(txt);

        var grupo = new Group(bola,bola2);
        txt.addBehavior(FollowBehavior.asSubtitleWithDelay(grupo, txt, 20, 1));


        bola.move(200, 0).andThen(bola.move(-200, 0)).repeat(10).executeInBackGround();
        bola.getRadius().change(100).andThen(bola.getRadius().change(-100)).repeat(10).execute();
    }

    public static void main(String[] args) {
        new Subtitle().build();
    }
}
