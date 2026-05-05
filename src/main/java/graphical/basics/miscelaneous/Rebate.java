package graphical.basics.miscelaneous;

import codec.engine.EngineType;
import graphical.basics.ColorHolder;
import graphical.basics.behaviors.FollowBehavior;
import graphical.basics.gobject.Circle;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.StringGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class Rebate extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
        presentationConfig.setFramerate(30);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildAnimation() {

        var counter = new StringGobject("a");
        counter.setPositionTo(Location.at(800,100));
        add(counter);
        counter.addBehavior(()->counter.set("g:"+getGobjects().size()));

        //getBackGround().setColor(new Color(0,0,0,10));
        Circle lt = null;
        for (int i = 0; i < 1000_0; i++) {

            var circle = CircleBuilder.aCircle()
                    .withRadius(5 + Math.random() * 5)
                    .withColor(ColorHolder.randomColor())
                    .withCenter(Location.at(Math.random() * 1000, Math.random() * 1000))
                    .build();
            add(circle);

            if (lt != null) {
                circle.addBehavior(FollowBehavior.followWithDelay(lt, circle, 0.2));
            }

            Circle finalLt = lt;
            circle.addBehavior(
                    new Object() {
                        double velx = 10 * Math.random() * (Math.random() < 0.5 ? 1 : -1);
                        double vely = 10 * Math.random() * (Math.random() < 0.5 ? 1 : -1);
                        Circle pointer = finalLt;
                    },
                    m -> {
                        var mid = circle.getMidPoint();

                        if (mid.getX() > 1000 || mid.getX() < 0) {
                            m.velx = -m.velx;
                        }
                        if (mid.getY() > 1000 || mid.getY() < 0) {
                            m.vely = -m.vely;
                        }
                        if (m.pointer != null) {
                            // circle.setPositionTo(Location.midPoint(mid, m.pointer.getMidPoint()));


                            var color1 = circle.getFillColor();
                            var color2 = m.pointer.getFillColor();


                        } else {
                            circle.changeSetPosition(m.velx, m.vely);
                        }
                        // circle.changeSetPosition(m.velx, m.vely);


                    }

            );

            var color = circle.getFillColor();

            circle.changeColor(Color.yellow)
                    .forSeconds(0.5).parallel(circle.getRadius().change(5).forSeconds(0.5))
                    .andThen(circle.changeColor(color)
                            .forSeconds(0.5).parallel(circle.getRadius().change(-5).forSeconds(0.5)))
                    .andThen(wait(seconds(2)))
                    .repeat(100)
                    .executeInBackGround();
            wait(1).execute();


//            if (Math.random() < 0.2) {
//                circle.changeColor(Color.yellow).forSeconds(0.3 + Math.random())
//                        .andThen(circle.changeColor(color).forSeconds(0.3 + Math.random()))
//                        .repeat(100)
//                        .executeInBackGround();
//
//            }
            lt = circle;
        }


        wait(seconds(60 * 10)).execute();


    }

    public static void main(String[] args) {
        new Rebate().build();
    }
}
