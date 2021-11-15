package graphical.basics.examples;

import codec.CodecType;
import codec.engine.EngineType;
import graphical.basics.*;
import graphical.basics.gobject.Circle;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.location.Location;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class EXAugusto extends Presentation {

    public static final int size = 500;

    @Override
    public void setup(PresentationConfig presentationConfig) {
        // presentationConfig.setDisableCodec(true);
        presentationConfig.setCodec(CodecType.GIF);
        presentationConfig.setFramerate(12);
        presentationConfig.setDisablePreview(true);
        presentationConfig.setHeight(size);
        presentationConfig.setWidth(size);
        presentationConfig.setEngine(EngineType.NATIVE_JAVA);
    }

    @Override
    protected void buildPresentation() {

        getBackGround().getBackGroundColor().setColor(new Color(0,0,0,0));

        //aqui vai o codigo
        getCamera().getScale().setValue(size / 1000.0);
        //  getCamera().setPositionTo(Location.at(500,500));

        Circle circle1 = CircleBuilder.aCircle()
                .withCenter(100, 250)
                .withColor(Color.red)
                .build();

        Circle circle2 = CircleBuilder.aCircle()
                .withCenter(400, 100)
                .withColor(Color.blue)
                .build();

        add(circle1);
        add(circle2);

        Animation.strokeAndFill(circle1).parallel(Animation.strokeAndFill(circle2)).execute();

        var task1 = circle1.move(300, 0).andThen(circle1.move(-300, 0)).repeat(5);
        var task2 = circle2.move(0, 300).andThen(circle2.move(0, -300)).repeat(5);

        task1.parallel(task2).execute();

        //
    }

    public static void main(String[] args) {
        new EXAugusto().build();
    }

}
