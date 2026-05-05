package graphical.basics.miscelaneous;

import graphical.basics.gobject.latex.Latex;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class TesteCode extends Animation {



    public static void main(String[] args) {
        new TesteCode().build();
    }

    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(false);
        presentationConfig.setFramerate(60);
    }

    @Override
    protected void buildAnimation() {
        var latex = Latex.of("string", 30);
        latex.setColor(Color.white);
        latex.setPositionTo(Location.at(500,500));
        add(latex);

    }
}

