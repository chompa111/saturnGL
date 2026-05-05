package graphical.basics.miscelaneous;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;

public class GrabBall extends RTAnimation {
    public void buildAnimation() {
        var ball = CircleBuilder.aCircle().build();
        add(ball);


        ball.addBehavior(()->{

        });


        addDragBehavior(ball);

    }

    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    public static void main(String[] args) {
        new GrabBall().buildAnimation();
    }
}
