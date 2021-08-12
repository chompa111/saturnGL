package graphical.basics.examples;

import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;


public class ShapeT extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true).setFramerate(30);
    }

    @Override
    public void buildPresentation() {
        var logo = new SVGGobject("C:\\Users\\PICHAU\\logao.svg").getGroup("planeta").toGroupGobject();

        //logo.changeSetPosition(0,300);


        var logo2 =new SVGGobject("C:\\Users\\PICHAU\\Desktop\\azuis.svg").getGroup("balao").toGroupGobject();


        logo.getGobjects().size();

        logo2.getGobjects().size();

        add(logo);

        Animation.t3b1b(logo,logo2,seconds(1)).execute();

        wait(1).execute();
        cut();
    }

    public static void main(String[] args) {
        new ShapeT().buildPresentation();
    }
}
