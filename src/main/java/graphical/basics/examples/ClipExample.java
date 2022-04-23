package graphical.basics.examples;

import codec.engine.EngineType;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Fonts;
import graphical.basics.gobject.TextGobject;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.ClipBox;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.effects.T3b1b;
import graphical.basics.value.ChangeType;

import java.awt.*;

public class ClipExample extends Presentation {

    public static final Location ORIGIN = Location.at(0, 0);
    public static final Location MID = Location.at(500, 500);

    @Override
    public void setup(PresentationConfig presentationConfig) {
          //presentationConfig.setDisableCodec(true);
      //  presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildPresentation() {

        var tex= new TextGobject("a+bi",ORIGIN,Color.orange);
        tex.setPositionTo(MID);
        add(tex);
        Animation.clipInit(tex).execute();
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
