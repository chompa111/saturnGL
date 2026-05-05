package graphical.basics.miscelaneous.regex;

import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

public class MainAnimationRegex extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
    }

    @Override
    protected void buildAnimation() {

        new OtherPart().buildPresentation();

    }

    public static void main(String[] args) {
        new MainAnimationRegex().build();
    }
}
