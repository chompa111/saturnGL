package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.WaitTask;
import graphical.basics.value.ChangeType;

import java.awt.*;

public class SVG2EXp extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {


    }

    public static void main(String[] args) {
        new SVG2EXp().build();
    }
}
