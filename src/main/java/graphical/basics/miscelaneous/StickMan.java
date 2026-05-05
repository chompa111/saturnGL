package graphical.basics.miscelaneous;

import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class StickMan extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
    }

    @Override
    protected void buildAnimation() {

        getBackGround().getBackGroundColor().setColor(Color.red);

        var stickpose1=new SVGGobject("C:\\Users\\PICHAU\\palitoik2.svg").toGroupGobject();
        stickpose1.setPositionTo(Location.at(500,500));
       // add(stickpose1);


        var stickpose2=new SVGGobject("C:\\Users\\PICHAU\\palitoik3.svg").toGroupGobject();
        stickpose2.setPositionTo(Location.at(500,500));
        //add(stickpose2);

        //var circle= CircleBuilder.aCircle().build();

        Animations.t3b1b(stickpose1,stickpose2,seconds(1)).execute();


    }

    public static void main(String[] args) {
        new StickMan().build();
    }
}
