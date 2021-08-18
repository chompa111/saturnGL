package graphical.basics.examples;

import graphical.basics.ColorHolder;
import graphical.basics.behavior.FollowBehavior;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.gobject.StringGobject;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Point;
import graphical.basics.location.SupplierPoint;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.transformation.value.ConstantSpeedValueTranform;
import graphical.basics.value.DoubleHolder;

import java.awt.*;
import java.util.Arrays;

public class CircleEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30);
        presentationConfig.setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {



      //  getBackGround().getColors().get(0).setColor(new Color(0,0,0,80));

        var b1=CircleBuilder.aCircle().build();
        var b2=CircleBuilder.aCircle().build();

        var cog= new SVGGobject("C:\\Users\\PICHAU\\cog.svg").toGroupGobject().getGobjects().get(0);
        var cog2= new SVGGobject("C:\\Users\\PICHAU\\cog.svg").toGroupGobject().getGobjects().get(0);
        var cog3= new SVGGobject("C:\\Users\\PICHAU\\cog.svg").toGroupGobject().getGobjects().get(0);

        cog2.changeSetPosition(250,300);
        cog3.changeSetPosition(20,305);
        cog3.getAngle().setValue(Math.toRadians(20));


        cog.changeSetPosition(235+250,300);
        cog.getAngle().setValue(Math.toRadians(20));
        Positioning.align(b1,cog, Positioning.Reference.CENTER);
        Positioning.align(b2,cog2, Positioning.Reference.CENTER);

        add(b1);
        add(b2);
        add(cog3);

        //add(cog);


        Animation.t3b1b(b2,cog2,seconds(1))
                .parallel(Animation.t3b1b(b1,cog,seconds(1)))
                .execute();


        var centerCog1=cog.getBorders().midPoint();
        var centerCog2=cog2.getBorders().midPoint();


        double dist=Math.sqrt((centerCog1.getX()-centerCog2.getX())*(centerCog1.getX()-centerCog2.getX())+(centerCog1.getY()-centerCog2.getY())*(centerCog1.getY()-centerCog2.getY()));


        var i=new DoubleHolder(0);
        var j=new DoubleHolder(Math.toRadians(180));

        var point=new SupplierPoint(()->dist*Math.cos(i.getValue()),()->dist*Math.sin(i.getValue()));
        var point2=new SupplierPoint(()->dist*Math.cos(j.getValue()),()->dist*Math.sin(j.getValue()));

        var follow=new FollowBehavior(cog,point);
        add(follow);

        add( new FollowBehavior(cog3,point2));

//        i.change(6.28,seconds(3))
//                .parallel(cog.rotate(2*6.28,seconds(3))).repeat(300).execute();

//        var camera=getCamera();
//        add( new FollowBehavior(camera,point2));
//
//        Positioning.align(camera,cog3, Positioning.Reference.CENTER);




        double f=1;
        double tempo=5;

        var group=new Group(cog,cog2,cog3);

        remove(cog);
        remove(cog2);
        remove(cog3);

        add(group);



        i.change(f*3.14,seconds(tempo))
               .parallel( j.change(f*3.14,seconds(tempo)))
                .parallel(cog3.rotate(-f*6.28,seconds(tempo)))
                .parallel(cog2.rotate(2*f*6.28,seconds(tempo)))
                //.parallel(Animation.wooble(group).repeat(3))
               // .parallel(group.move(200,0).andThen(group.move(-200,0)).repeat(2))
                .parallel(cog.changeColor(Color.red).andThen(cog.changeColor(Color.blue)).repeat(2))
                .parallel(cog2.changeColor(Color.magenta,seconds(0.5)).andThen(cog2.changeColor(Color.green,seconds(0.5))).repeat(2))
                .parallel(cog3.changeColor(Color.cyan).andThen(cog3.changeColor(Color.WHITE)).repeat(2))
                .parallel(cog.rotate(-f*6.28,seconds(tempo))).repeat(100).executeInBackGround();


        wait(seconds(10)).execute();
        cut();
        wait(seconds(10)).execute();
        cut();
        wait(seconds(10)).execute();




















        cut();
    }

    public static void main(String[] args) {

        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (int i = 0; i < fonts.length; i++) {
            System.out.println(fonts[i]);
        }
        new CircleEx().buildPresentation();
    }
}
