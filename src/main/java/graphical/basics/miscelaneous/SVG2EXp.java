package graphical.basics.miscelaneous;

import codec.engine.EngineType;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.SupplierTask;
import graphical.basics.task.Task;

import java.awt.*;

public class SVG2EXp extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(false);
        presentationConfig.setHeight(1000);
        presentationConfig.setWidth(1000);
        presentationConfig.setFramerate(60);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    public void buildAnimation() {

        var tratorSvg = new SVGGobject("C:\\Users\\PICHAU\\trato.svg");

        var rodaT = tratorSvg.getGroup("rodaT").toGroupGobject();
        var rodaF = tratorSvg.getGroup("rodaF").toGroupGobject();

        var chassi = tratorSvg.getGroupExcept("rodaT", "rodaF").toGroupGobject();


        var trator = new Group(chassi, rodaF, rodaT);
        add(trator);


        //trator.setPositionTo(Location.at(0,0));
       // trator.changeSetPosition(-300,0);

        Animations.strokeAndFill(trator, seconds(1)).execute();
        // chassi.changeColor(new Color(64, 156, 31)).execute();

        // Animation.t3b1b(trator, CircleBuilder.aCircle().build(), seconds(2)).execute();

        var t = 4;
        var tempo=10;
        var frente = rodaT.getAngle().change(Math.toRadians(t * 360), seconds(tempo))
                .parallel(fumo(Location.at(500,500)))
                .parallel(rodaF.getAngle().change(Math.toRadians(t * 360 * 1.52), seconds(tempo)))
                .parallel(trator.move(t * 114 * Math.PI, 0, seconds(tempo)))
                .step(()->trator.changeSetPosition(-t * 114 * Math.PI,0));

//        var verso = rodaT.getAngle().change(-Math.toRadians(t * 360), seconds(tempo))
//                .parallel(rodaF.getAngle().change(-Math.toRadians(t * 360 * 1.52), seconds(tempo)))
//                .parallel(trator.move(-t * 114 * Math.PI, 0, seconds(tempo)));


        frente.repeat(10).executeInBackGround();

       while (true){
           for(int i=0;i<27;i++){
               var location=rodaF.getBorders().midPoint();
               for(int j=0;j<10;j++){

                   fumo(Location.at(location.getX(),location.getY()-200)).executeInBackGround();
               }
               wait(1).execute();
           }
           wait(seconds(0.2)).execute();
       }


    }

    Task fumo(Location location) {
        return new SupplierTask(() -> {
            var bolinha = CircleBuilder.aCircle()
                    .withRadius(5+Math.random()*20)
                    .withCenter(Location.at(location.getX(), location.getY()))
                    .withColor(new Color(180, 180, 180, (int) (Math.random() * 255)))
                    //.withColor(ColorHolder.randomColor())
                    .build();

            add(bolinha);
            return bolinha.move(-(30+Math.random() * 200), Math.random() * 100 - 50)
                    .parallel(Animations.fadeOut(bolinha))
                    .step(() -> {
                remove(bolinha);
            });
        });

    }

    public static void main(String[] args) {
        new SVG2EXp().build();
    }
}
