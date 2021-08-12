package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class Ex extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
        presentationConfig.setFramerate(30);
        presentationConfig.setHeight(1000);
        presentationConfig.setWidth(1000);
    }

    @Override
    public void buildPresentation() {

    }

    public static void main(String[] args) {
        new Ex().buildPresentation();
    }
}
