package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.WaitTask;

import java.awt.*;

public class SVG2EXp extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(10);
        presentationConfig.setDisableCodec(false);
    }

    @Override
    public void buildPresentation() {
        //getBackGround().getColors().get(0).setColor(Color.white);
        var svg=new SVGGobject("C:\\Users\\PICHAU\\Desktop\\azuis.svg");
        var balao=svg.getGroup("balao");
        add(balao);

        Animation.strokeAndFill(balao,seconds(4)).execute();


        new WaitTask(1).execute();
        cut();

    }

    public static void main(String[] args) {
        new SVG2EXp().buildPresentation();
    }
}
