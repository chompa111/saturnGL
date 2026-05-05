package graphical.basics.miscelaneous;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Polygon;
import graphical.basics.gobject.Rect;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class Square extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        // configs
//        presentationConfig.setDisableCodec(true);
//        presentationConfig.setCodec(.GI;
    }

    @Override
    public void buildAnimation() {


        var bolinha = CircleBuilder.aCircle()
                .withCenter(Location.at(500, 500))
                .withRadius(200)
                .withColor(Color.blue)
                .build();

        var quadradinho = new Rect(Location.at(500,500).plus(-100,-100),Location.at(500,500).plus(100,100),Color.white);
        quadradinho.getAngle().setValue(Math.PI/2);
        add(quadradinho);

        var pentagon = new Polygon(Color.blue,Location.at(0,100),
                Location.at(95.1,30.9),
                Location.at(58.7,-80.9),
                Location.at(-58.7,-80.9),
                Location.at(-95.1,30.9));
        pentagon.setPositionTo(Location.at(700,500));

        wait(seconds(1)).execute();
        Animations.t3b1b(quadradinho,bolinha,seconds(5)).execute();


//        this.addClickListener(quadradinho,Trigger.once(()->quadradinho.changeColor(Color.red).andThen(quadradinho.changeColor(Color.blue)).executeInBackGround()));

    }


    public static void main(String[] args) {
        new Square().build();
    }
}
