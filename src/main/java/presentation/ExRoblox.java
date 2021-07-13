package presentation;

import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.Circle;
import graphical.basics.gobject.Gobject;
import graphical.basics.gobject.StringGobject;
import graphical.basics.location.Point;
import graphical.basics.task.CodeTask;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.value.DoubleHolder;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExRoblox extends Presentation {


    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    public void buildPresentation() {

        List<Gobject> gobjectList= new LinkedList<>();

        for(int i=1;i<=7;i++){
            for(int j=1;j<=7;j++){
                var circle = new Circle(new Point(i*100+100,j*100+100), new DoubleHolder(100),Color.white);
                var pepe =0;
                if(i==(8-pepe-j)||i==(15-pepe-j)){
               // if(i==(13-j)){
                    circle.setColorHolder(new ColorHolder(new Color(0,0,255)));
                }
                if(i+(7-pepe)==j||i-pepe==j){
                    // if(i==(13-j)){
                    circle.setColorHolder(new ColorHolder(new Color(0,255,0)));
                }
//
//                if((1.0/3)*i==(j-6)){
//                    // if(i==(13-j)){
//                    circle.setColorHolder(new ColorHolder(Color.yellow));
//                }


                final int pepe2=((j-1)*7+i);
                var text = new StringGobject(new Point(i*100+100,j*100+100),()->""+pepe2,Color.red);


                add(circle);
                gobjectList.add(circle);
                add(text);
            }
        }
    new CodeTask(()->{
        for (int pepe =0;pepe<7;pepe++){

            var list = new ArrayList<Task>();
            for(int i=1;i<=7;i++){
                for(int j=1;j<=7;j++){
                    var index=((j-1)*7+i);
                    var circle = (Circle)gobjectList.get(index-1);
                    if(i==(8-pepe-j)||i==(15-pepe-j)){
                        // if(i==(13-j)){
                        list.add(circle.changeColor(new Color(0,0,255)).andThen(circle.changeColor(Color.white)));
                    }
                    if(i+(7-pepe)==j||i-pepe==j){
                        // if(i==(13-j)){
                        list.add(circle.changeColor(new Color(0,255,0)).andThen(circle.changeColor(Color.white)));
                    }
//
                }
            }
            CodeTask.runTask(new ParalelTask(list));
            CodeTask.doStep();
        }

    }).execute();



        new WaitTask(1).execute();


        cut();
    }

    public static void main(String[] args) {
        new ExRoblox().buildPresentation();
    }
}
