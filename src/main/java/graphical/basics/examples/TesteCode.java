package graphical.basics.examples;

import graphical.basics.gobject.Video;
import graphical.basics.location.Location;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class TesteCode extends Presentation {



    public static void main(String[] args) {
        new TesteCode().build();
    }

    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(false);
        presentationConfig.setFramerate(60);
    }

    @Override
    protected void buildPresentation() {
       // getBackGround().getBackGroundColor().setColor(Color.red);

        wait(seconds(1)).execute();
        Video v = new Video(Location.at(500,500),"C:\\Users\\PICHAU\\Videos\\pepe2.mov");
        v.scale.setValue(0.0);
        add(v);

        v.getScale().change(0.5,seconds(3)).execute();
        v.play(seconds(10)).execute();
    }
}

