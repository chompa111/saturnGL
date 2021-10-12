package graphical.basics.examples;

import codec.CodecType;
import codec.engine.EngineType;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Pencil;
import graphical.basics.gobject.TextGobject;
import graphical.basics.gobject.Video;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.value.ChangeType;

import java.awt.*;

public class Quadrado extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

        presentationConfig.setDisableCodec(false);
        presentationConfig.setHeight(1000);
        presentationConfig.setWidth(1000);
        presentationConfig.setFramerate(30);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildPresentation() {


        var moco= new SVGGobject("C:\\Users\\PICHAU\\Desktop\\urso.svg").toGroupGobject();
        moco.setPositionTo(Location.at(500,500));
        add(moco);


        Animation.strokeAndFill(moco,seconds(3)).execute();

////
//
//     //   getBackGround().changeColor(Color.red).execute();

//        //video.setPositionTo(Location.at(500,500));
//      //  video.getScale().setValue(0.3);
//        video.setPositionTo(Location.at(500,500));



        //Animation.fadeInGrow(video,seconds(2)).execute();
//        video.play(seconds(15)).executeInBackGround();
//        video.getScale().change(-0.8,seconds(2)).execute();
//
//        video.moveTo(Location.at(200,200)).execute();




//
//        video.getScale().change(-0.8).andThen(  video.getScale().change(0.8)).repeat(3).executeInBackGround();
//        video.play(seconds(300)).executeInBackGround();
//
//
////
//        var video2 = new Video(Location.at(100,100),"C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\my_videos\\maquina.mp4");
//        add(video2);
//
//        video2.getScale().setValue(0.5);
//        video2.changeSetPosition(-200,0);
//        video2.play(seconds(300)).executeInBackGround();
//        video.getAngle().change(0.003,seconds(300),ChangeType.CONSTANT_SPEED).execute();

        //video.getAngle().change(0.1,seconds(10),ChangeType.CONSTANT_SPEED).execute();
//        var rect = new Rect(Location.at(100,100),Location.at(300,300), Color.red);
//
//
//        rect.setStrokeColorHolder(new ColorHolder(Color.white));
//
//
//        rect.getAngle().setValue(Math.toRadians(90));
//
//        var circle= CircleBuilder.aCircle().withColor(Color.green).build();
//        rect.setPositionTo(circle.getBorders().midPoint());
//        add(circle);
//        Animation.strokeAndFill(circle,seconds(1)).execute();
//
//
//        Animation.t3b1b(circle,rect,seconds(1)).execute();
//        var text=new TextGobject("L^2+1",Location.at(500,500),Color.white);
//        wait(seconds(1)).execute();
//       rect.transform(text,seconds(1)).execute();



    }

    public static void main(String[] args) {
        new Quadrado().build();
    }
}
