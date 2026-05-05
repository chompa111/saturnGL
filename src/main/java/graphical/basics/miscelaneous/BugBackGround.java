package graphical.basics.miscelaneous;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

public class BugBackGround extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    protected void buildAnimation() {
        var b = CircleBuilder.aCircle().build();
        add(b);


        b.move(1000,0).forSeconds(100).executeInBackGround();
    }

    public static void main(String[] args) {
        new BugBackGround().buildAnimation();
    }
}
