package graphical.basics.presentation;

import graphical.basics.gobject.CircleBuilder;

import java.awt.*;

public class HelloWorld implements AnimationContext{


    void batata(){

        var c = CircleBuilder.aCircle().build();
        add(c);

        c.changeColor(Color.red).forSeconds(2);
        c.changeColor(Color.green).forSeconds(3).execute();
    }

    public static void main(String[] args) {
        DefaultAnimationStarter.startEnv(x->x.setWidth(2000));
        new HelloWorld().batata();
    }
}
