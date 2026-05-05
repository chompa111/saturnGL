package graphical.basics.miscelaneous;

import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

public class PandaEx extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
    }

    @Override
    protected void buildAnimation() {

       // getBackGround().getBackGroundColor().setColor(Color.white);
            var svg= new SVGGobject("C:\\Users\\PICHAU\\Desktop\\cp.svg").toGroupGobject();

        var trato= new SVGGobject("C:\\Users\\PICHAU\\trato.svg").toGroupGobject();
        trato.setPositionTo(Location.at(500,500));



        add(svg);

        Animations.t3b1b(svg,trato,seconds(3)).execute();


       // svg.onChildren(x->x.moveTo(Location.at(Math.random()*1000,Math.random()*1000))).execute();

    }

    public static void main(String[] args) {
        new PandaEx().build();
    }
}
