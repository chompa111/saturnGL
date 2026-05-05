package graphical.basics.miscelaneous;

import graphical.basics.gobject.TextGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class ClipExample extends Animation {

    public static final Location ORIGIN = Location.at(0, 0);
    public static final Location MID = Location.at(500, 500);

    @Override
    public void setup(PresentationConfig presentationConfig) {
          //presentationConfig.setDisableCodec(true);
      //  presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildAnimation() {

        var tex= new TextGobject("a+bi",ORIGIN,Color.orange);
        tex.setPositionTo(MID);
        add(tex);
        Animations.clipInit(tex).execute();
        //TextGobject.indexsize(tex);

        tex.subGroup(0).changeColor(Color.green).executeInBackGround();
        tex.subGroup(2).changeColor(Color.red).execute();

//
//        clipbox.add(circle);
//
//
//        var circle2 = CircleBuilder.aCircle().withColor(Color.red).withRadius(40).build();
//        add(circle2);
//        circle2.move(400,400,seconds(3)).executeInBackGround();
//
//        circle.move(300,0,seconds(3)).execute();


    }

    public static void main(String[] args) {
        new ClipExample().build();
    }
}
