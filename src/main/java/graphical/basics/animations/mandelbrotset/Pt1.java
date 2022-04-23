package graphical.basics.animations.mandelbrotset;

import codec.CodecType;
import codec.engine.EngineType;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.*;
import graphical.basics.gobject.Image;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class Pt1 extends Presentation {

    public static Location MID = Location.at(1920.0 / 2, 1080.0 / 2);
    public static Location ORIGIN = Location.at(0, 0);
    


    @Override
    public void setup(PresentationConfig presentationConfig) {
        //   presentationConfig.setDisableCodec(true);
        //presentationConfig.setDisablePreview(true);
        presentationConfig.setCodec(CodecType.XUGGLE);
        presentationConfig.setEngine(EngineType.JAVAFX);
        presentationConfig.setWidth(1920);
        presentationConfig.setHeight(1080);
    }

    @Override
    protected void buildPresentation() {

        // switchOff();

        Image i = new Image(MID.plus(250, -150), "C:\\Users\\PICHAU\\Desktop\\video-mandel\\gafUL.jpg");
        i.scale.setValue(0.3);
        Animation.clipInit(i).executeInBackGround();

        //wait(seconds(0.2)).execute();


        Image i3 = new Image(MID.plus(-250, -150), "C:\\Users\\PICHAU\\Desktop\\video-mandel\\mandel-25.jpg");
        i3.scale.setValue(0.3);


        Animation.clipInit(i3).executeInBackGround();

        // wait(seconds(0.2)).execute();


        Image i2 = new Image(MID.plus(-250, 200), "C:\\Users\\PICHAU\\Desktop\\video-mandel\\Mandelbrot_set_image.png");
        i2.scale.setValue(0.35);
        Animation.clipInit(i2).executeInBackGround();

        //  wait(seconds(0.2)).execute();

        Image i4 = new Image(MID.plus(250, 200), "C:\\Users\\PICHAU\\Desktop\\video-mandel\\800wm.jpg");
        i4.scale.setValue(0.55);
        Animation.clipInit(i4).execute();


        //wait(seconds(10)).execute();

        cut();


        remove(i, i2, i3, i4);
        Group g = new Group(i, i2, i3, i4);
        add(g);

        g.getScale().change(-0.5, seconds(1)).executeInBackGround();
        g.moveTo(MID.plus(-300, 0), seconds(1)).execute();

        var border = g.getBorders();
        Rect r = new Rect(border.getL1(), border.getL2(), new Color(0, 0, 0, 0));
        r.setStrokeColorHolder(new ColorHolder(Color.white));
        r.getStrokeThickness().setValue(2);
        r.getScale().setValue(1.2);

        addBefore(g, r);
        Animation.strokeAndFill(r, seconds(2)).executeInBackGround();


        var conjuntoMandelbrot = new TextGobject(Fonts.COMPUTER_MODERN_I.deriveFont(40f), "Conjunto de Mandelbrot", MID, Color.white);


        switchOn();


        conjuntoMandelbrot.setPositionTo(r.getBorders().midPoint().plus(0, -230));
        add(conjuntoMandelbrot);

        conjuntoMandelbrot.write(seconds(0.5) + 1, 2).execute();


        var benoit = new SVGGobject("C:\\Users\\PICHAU\\Desktop\\mandelpao.svg");
        benoit.setPositionTo(MID.plus(200, 0));
        benoit.getScale().setValue(1.5);

        var dates = new TextGobject(Fonts.COMPUTER_MODERN_I.deriveFont(30f), "Benoit Mandelbrot", benoit.getBorders().midPoint().plus(-150, 150), Color.white);


        var fotoAndDate = new Group(dates, benoit);


        add(fotoAndDate);
        Animation.fadeInGrow(fotoAndDate, seconds(2)).execute();

//
//        var conjunto_aux = new TextGobject(Fonts.COMPUTER_MODERN_I.deriveFont(40f), "Conjunto de Mandelbrot", MID, Color.white);
//        conjunto_aux.setPositionTo(r.getBorders().midPoint().plus(0, -230));
//        var conjunto = conjunto_aux.subGroup(0, 1, 2,3, 4, 5, 6, 7);
//        add(conjunto);
//
//        conjunto.move(700, 100, seconds(2)).execute();

        wait(seconds(10)).execute();




    }

    public static void main(String[] args) {
        new Pt1().build();
    }
}
