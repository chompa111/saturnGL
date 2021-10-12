package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class EXAugusto extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(false);
    }

    @Override
    protected void buildPresentation() {
        //aqui vai o codigo

        var bolinha = CircleBuilder.aCircle()
                .withColor(Color.red)
                .build();

        add(bolinha);

        Animation.strokeAndFill(bolinha,seconds(1)).execute();

        var quadrado= new Rect(new Point(200,200), new Point(300,300), Color.orange);
        add(quadrado);

        Animation.strokeAndFill(quadrado,seconds(1)).execute();
        Animation.wooble(quadrado).execute();
        Animation.t3b1b(bolinha,quadrado,seconds(2)).execute();

    }

    public static void main(String[] args) {
        new EXAugusto().build();
    }

}
