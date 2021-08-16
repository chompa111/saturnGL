package graphical.basics.examples;

import codec.CodecType;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.DynamicPath;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.transformation.gobject.PositionListTransform;
import graphical.basics.task.transformation.gobject.PositionTransform;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

public class Paths extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
        presentationConfig.setFramerate(30);
        presentationConfig.setHeight(1000);
        presentationConfig.setWidth(1000);

        presentationConfig.setCodec(CodecType.RAW_IMAGE);
    }

    @Override
    public void buildPresentation() {

        GeneralPath shape = new GeneralPath();
        shape.setWindingRule(Path2D.WIND_EVEN_ODD);
        shape.moveTo(500, 500);

        shape.lineTo(600, 500);
        shape.lineTo(600, 600);
        shape.lineTo(500, 600);
        shape.lineTo(500, 500);
        //shape.closePath();

        shape.moveTo(550, 550);
        shape.lineTo(575, 550);
        shape.lineTo(575, 575);
        shape.lineTo(550, 575);
        shape.lineTo(550, 550);
        shape.closePath();


        GeneralPath shape2 = new GeneralPath();
        shape2.setWindingRule(Path2D.WIND_EVEN_ODD);

        shape2.moveTo(500, 500);
        shape2.lineTo(550, 500);
        shape2.lineTo(560, 500);
        shape2.lineTo(570, 500);
        shape2.lineTo(600, 500);
        shape2.lineTo(600, 550);
        shape2.lineTo(600, 600);
        shape2.lineTo(550, 600);
        shape2.lineTo(500, 600);
        shape2.lineTo(500, 550);
        shape2.lineTo(500, 500);

        var sg2 = new DynamicPath(shape2, new ColorHolder(Color.blue), new ColorHolder(Color.blue));


        sg2.changeSetPosition(150, 0);

        //add(sg2);


        // var sg = new ShapeGobject2(shape, Color.red, Color.red);


        var sg = new DynamicPath(shape, new ColorHolder(Color.red), new ColorHolder(Color.red));
        add(sg2);
        //Animation.strokeAndFill(sg,seconds(10)).execute();

        //sg.move(200,0).execute();

        new PositionListTransform(sg2.getRefereceLocations(),sg.getRefereceLocations(),seconds(1)).execute();

        wait(1).execute();

    }

    public static void main(String[] args) {
        new Paths().buildPresentation();
    }
}
