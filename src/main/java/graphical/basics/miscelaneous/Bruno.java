package graphical.basics.miscelaneous;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.transformation.gobject.CustomSpaceColorTransform;

import java.awt.*;

public class Bruno extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        // configs
        presentationConfig.setDisableCodec(true);
    }

    @Override
    protected void buildAnimation() {


        var bolinha = CircleBuilder.aCircle()
                .withCenter(Location.at(500, 500))
                .withRadius(100)
                .withColor(Color.blue)
                .build();

        var bolinha2 = CircleBuilder.aCircle()
                .withCenter(Location.at(500, 650))
                .withRadius(100)
                .withColor(Color.blue)
                .build();

        add(bolinha);
        add(bolinha2);

        new CustomSpaceColorTransform(bolinha, Color.yellow,
                (c) -> new float[]{c.getRed(), c.getGreen(), c.getBlue()},
                (c) -> new Color((int) c[0], (int) c[1], (int) c[2]),
                seconds(3))
                .executeInBackGround();

        bolinha2.changeColor(Color.yellow).forSeconds(3).execute();

    }


    public static void main(String[] args) {
        new Bruno().build();
    }
}
