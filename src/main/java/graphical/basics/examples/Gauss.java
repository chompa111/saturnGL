package graphical.basics.examples;

import graphical.basics.gobject.TextGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gauss extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
        presentationConfig.setFramerate(30);
    }

    @Override
    public void buildPresentation() {

        //switchOff();
        var step1 = new TextGobject("1+2+3+4+5+6+...+n", new Point(0, 0),Color.white);


        var um = step1.subGroup(0);
        add(um);
        Animation.fadeInGrow(um, seconds(1)).execute();

        List<Task> taskList= new ArrayList<>();

        for(int i=1;i<step1.getGobjects().size();i+=2){

            int finalI = i;
            var t1= new ContextSetupTask(()->{
                var n=step1.subGroup(finalI,finalI+1);
                add(n);
                return Animation.fadeInGrow(n,seconds(1));
            });

            taskList.add(wait(seconds(Math.log(i+1)/Math.log(1.5))).andThen(t1));
        }

        execute(new ParalelTask(taskList));

        TextGobject.indexsize(step1);

        //switchOn();

        var step12 = new TextGobject("1+2+3+4+5+6+...+(n-1)+n", new Point(0, 0),Color.white);
//        add(step12);
//        LatexGobject.indexsize(step12);


        var step12M= step12.subGroup(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,21,22);
        var step12Except= step12.subGroupExept(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,21,22);
        Positioning.alignAll(step1.getGobjects(),step12M.getGobjects()).execute();

        add(step12Except);
        Animation.fadeInGrow(step12Except,seconds(1)).execute();

        var step2=new TextGobject("1+n", new Point(0, 100),Color.white);

//       Positioning.alignAll( step1.subGroup(0,19,20).getGobjects(), step2.getGobjects()).execute();
//
//       wait(seconds(0.5)).execute();
//
//       step1.subGroup(1).changeColor(new Color(0,0,0)).parallel(step1.subGroupExept(0,1,19,20).move(-80,0)).execute();





        //Animation.strokeAndFill(step1, seconds(1)).execute();
//        var um=step1.subGroup(0);
//        add(um);
//        Animation.fadeInGrow(um,seconds(1)).execute();
//
//        new WaitTask(seconds(0.5)).execute();
//
//        var dois=step1.subGroup(1,2);
//        add(dois);
//        Animation.fadeInGrow(dois,seconds(1)).execute();
//
//        var tres=step1.subGroup(3,4);
//        add(tres);
//        Animation.fadeInGrow(tres,seconds(1)).execute();
//
//        var quatro=step1.subGroup(5,6);
//        add(quatro);
//        Animation.fadeInGrow(quatro,seconds(1)).execute();



//
//        var t2= new ContextSetupTask(()->{
//            var n=step1.subGroup(9,10);
//            add(n);
//            return Animation.fadeInGrow(n,seconds(1));
//        });
//
//        var t3= new ContextSetupTask(()->{
//            var n=step1.subGroup(11,12);
//            add(n);
//            return Animation.fadeInGrow(n,seconds(1));
//        });
//
//        t1.parallel(wait(seconds(0.3))
//                .andThen(t2))
//                .parallel(wait(seconds(0.6)).andThen(t3))
//                .execute();








        new WaitTask(1).execute();
        cut();

    }

    public static void main(String[] args) {
        new Gauss().buildPresentation();
    }
}
