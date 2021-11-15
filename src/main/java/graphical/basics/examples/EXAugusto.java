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



    @Override
    public void setup(PresentationConfig presentationConfig) {
       //  presentationConfig.setDisableCodec(true);
        presentationConfig.setCodec(CodecType.RAW_IMAGE);
        presentationConfig.setFramerate(12);
        presentationConfig.setDisablePreview(false);
        presentationConfig.setHeight(1000);
        presentationConfig.setWidth(1000);
        presentationConfig.setEngine(EngineType.NATIVE_JAVA);
        presentationConfig.setPreviewWindowBarVisible(false);
        presentationConfig.setScale(0.5);
    }

    @Override
    protected void buildPresentation() {
                //aqui vai o codigo



        Circle circle1 = CircleBuilder.aCircle()
                .withCenter(500, 500)
                .withColor(Color.red)
                .build();

        Circle circle2 = CircleBuilder.aCircle()
                .withCenter(1000, 500)
                .withColor(Color.orange)
                .build();

        add(circle1);
        add(circle2);

        getCamera().getAngle().change(Math.toRadians(90),seconds(2)).execute();




//        getBackGround().getBackGroundColor().setColor(new Color(0,0,0,0));
//

//        //  getCamera().setPositionTo(Location.at(500,500));
//
//        Circle circle1 = CircleBuilder.aCircle()
//                .withCenter(100, 250)
//                .withColor(Color.red)
//                .build();
//
//        Circle circle2 = CircleBuilder.aCircle()
//                .withCenter(400, 100)
//                .withColor(Color.blue)
//                .build();
//
//        add(circle1);
//        add(circle2);
//
//        Animation.strokeAndFill(circle1).parallel(Animation.strokeAndFill(circle2)).execute();
//
//        var task1 = circle1.move(300, 0).andThen(circle1.move(-300, 0)).repeat(5);
//        var task2 = circle2.move(0, 300).andThen(circle2.move(0, -300)).repeat(5);
//
//        task1.parallel(task2).execute();

        //
    }

    public static void main(String[] args) {
        new EXAugusto().build();
    }

}
