package graphical.basics.examples;

import graphical.basics.ColorHolder;
import graphical.basics.Pivot;
import graphical.basics.gobject.Circle;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.WaitTask;
import graphical.basics.value.ChangeType;

import java.awt.*;

public class SVG2EXp extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {

        var leao= new SVGGobject("C:\\Users\\PICHAU\\leao.svg").toGroupGobject();
        add(leao);

//        for(int i=0;i<leao.getGobjects().size();i++){
//            var parte=leao.getGobjects().get(i);
//
//            parte.moveTo(Location.at((int)(Math.random()*1000),(int)(Math.random()*1000))).execute();
//        }

        wait(seconds(1)).execute();



        var circle= CircleBuilder.aCircle().withColor(Color.white).build();

        Animation.t3b1b(leao,circle,seconds(1)).execute();




//
//        for(int i=0;i<10;i++){
//            var rect1= new Rect(Location.at(100,100),Location.at(200,200), ColorHolder.randomColor());
//            rect1.setPositionTo(Location.at(500+(i%2)*100,500+(i%3)*100));
//            add(rect1);
//            rect1.getAngle().change(0.1+i/1000.0,seconds(100),ChangeType.CONSTANT_SPEED).executeInBackGround();
//
//        }
//        var rect1= new Rect(Location.at(100,100),Location.at(200,200),Color.red);
//        var rect2= new Rect(Location.at(100,100),Location.at(200,200),Color.orange);
//        var rect3= new Rect(Location.at(100,100),Location.at(200,200),Color.green);
//
//
//        rect1.setPositionTo(Location.at(500,500));
//        rect2.setPositionTo(Location.at(500,500));
//        rect3.setPositionTo(Location.at(500,500));
//
//        add(rect1);
//        add(rect2);
//        add(rect3);
//
//        rect1.getAngle().change(0.1,seconds(100),ChangeType.CONSTANT_SPEED).executeInBackGround();
//        rect2.getAngle().change(0.11,seconds(100),ChangeType.CONSTANT_SPEED).executeInBackGround();
//        rect3.getAngle().change(0.12,seconds(100),ChangeType.CONSTANT_SPEED).executeInBackGround();




    }

    public static void main(String[] args) {
        new SVG2EXp().build();
    }
}
