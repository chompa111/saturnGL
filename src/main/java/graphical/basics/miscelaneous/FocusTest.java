package graphical.basics.miscelaneous;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;

import java.awt.*;

public class FocusTest extends RTAnimation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    public void buildAnimation() {
        var ball = CircleBuilder.aCircle().build();
        add(ball);

        onFocus(ball, () -> ball.setColor(Color.red));
        outOfFocus(ball, () -> ball.setColor(Color.blue));

        addDragBehavior(ball);


    }

    public static void main(String[] args) {
        new FocusTest().buildAnimation();
    }
}
