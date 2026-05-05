package graphical.basics.miscelaneous;

import codec.engine.EngineType;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Rect;
import graphical.basics.gobject.struct.FillAndStroke;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class RectEx extends Animation {



    @Override
    public void setup(PresentationConfig presentationConfig) {
        // presentationConfig.setDisableCodec(true);
         presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildAnimation() {
                //aqui vai o codigo

        add(new Rect(Location.at(0,0),Location.at(1920,1080),Color.black));



        FillAndStroke rect = new Rect(Location.at(500,500),Location.at(600,600),new Color(0,0,0,0));

        rect= CircleBuilder.aCircle().withColor(new ColorHolder(new Color(0,0,255))).build();


        add(rect);
        rect.setStrokeColorHolder(new ColorHolder(new Color(0,0,0,0)));
        rect.getStroke().enableDash();
        rect.setStrokeThickness(new DoubleHolder(1));
        rect.getStroke().getDashWidth().setValue(3.14*10);
        rect.getStrokeColorHolder().changeColor(Color.orange).execute();






       // rect.getStrokeThickness().change(3,seconds(5)).executeInBackGround();
   //     rect.getStroke().getDashPhase().change(31.4*2,seconds(0.5)+1).andThen(wait(seconds(0.5))).repeat(10).executeInBackGround();
       // rect.getAngle().change(Math.toRadians(-45),seconds(2),ChangeType.MEAN_SPEED).repeat(3).executeInBackGround();
      //rect.getScale().changeTo(5,seconds(4)).execute();
      //  wait(seconds(15)).execute();

//
//        BlurredGroup bg= new BlurredGroup();
//        bg.addAll(this.getGobjects());
//
//        add(bg);
//
//        bg.getRadius().changeTo(100).execute();





    }

    public static void main(String[] args) {
        new RectEx().build();
    }

}
