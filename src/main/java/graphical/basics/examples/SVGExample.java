package graphical.basics.examples;

import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

public class SVGExample extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {
        var logo = new SVGGobject("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\src\\main\\resources\\saturn-font-logo.svg");

        add(logo);

        Animation.strokeAndFill(logo,seconds(3)).execute();
        Animation.fadeInGrow(logo,seconds(1)).execute();

        cut();
    }

    public static void main(String[] args) {
        new SVGExample().buildPresentation();
    }
}
