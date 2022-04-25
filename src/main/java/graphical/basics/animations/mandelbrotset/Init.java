package graphical.basics.animations.mandelbrotset;

import codec.engine.EngineType;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.*;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class Init extends Presentation {
    public static Location MID = Location.at(1920.0 / 2, 1080.0 / 2);
    public static Location ORIGIN = Location.at(0, 0);

    @Override
    public void setup(PresentationConfig presentationConfig) {
       // presentationConfig.setDisableCodec(true);
        presentationConfig.setEngine(EngineType.JAVAFX);
        presentationConfig.setWidth(1920);
        presentationConfig.setHeight(1080);
    }

    @Override
    protected void buildPresentation() {

        getBackGround().getBackGroundColor().setColor(new Color(0,255,0));

        SVGGobject aux = new SVGGobject("C:\\Users\\PICHAU\\Desktop\\icone.svg");
        aux.setPositionTo(MID);
        var bola = aux.getGroup("bola");
        var resto = aux.getGroupExcept("bola", "texto").toGroupGobject();
        var texto = aux.getGroup("texto").toGroupGobject();

        bola.changeSetPosition(0, -1180);

        Light l = new Light(MID,new DoubleHolder(1000),new ColorHolder(new Color(139, 22, 139,0)));
        add(l);

        add(bola);

        bola.move(0, 1280, seconds(1)).execute();
        bola.move(0, -120, seconds(0.75)).andThen(
                bola.move(0, 20, seconds(0.5))
        ).executeInBackGround();



        add(resto);
        resto.onChildren(x -> Animation.strokeAndFill(x, seconds(2)), 2).executeInBackGround();
        wait(seconds(2.5)).execute();
        add(texto);
        Animation.fadeIn(texto, seconds(1)).executeInBackGround();
        l.changeColor(new Color(62, 1, 75,200)).execute();
        wait(seconds(4)).execute();





    }

    public static void main(String[] args) {
        new Init().build();
    }
}
