package presentation;

import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.gobject.Ball;
import graphical.basics.gobject.Gobject;
import graphical.basics.gobject.Group;
import graphical.basics.location.Point;
import graphical.basics.task.CodeTask;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.PositionTransform;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GroupEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    public void buildPresentation() {
        List<Gobject> balls = new ArrayList<>();
        List<Gobject> even = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                var ball = new Ball(new Point(i * 100 + 30, j * 100 + 30),(Math.random() * 20) - 10, (Math.random() * 20) - 10, Color.white);
                balls.add(ball);
                if (i % 2 == 0) {
                    even.add(ball);
                }
            }
        }

        var group = new Group(balls);
        var eveng = new Group(even);

        add(group);

        // execute(new ColorTranform(eveng, Color.magenta,seconds(1)));



        execute(new WaitTask(seconds(1)));
        cut();
        execute(new ColorTranform(eveng, Color.yellow, seconds(1)).andThen(new PositionTransform(eveng, 50, 0, seconds(0.2))));
        cut();
        execute(new CodeTask(() -> {
            List<Task> taskList= new ArrayList<>();

          for (int i=0;i<balls.size();i++){
              var ball=balls.get(i);
              taskList.add(new WaitTask(i+1)
                      .andThen(new PositionTransform(ball,0,100,seconds(1))
                      .andThen( new PositionTransform(ball,0,-100,seconds(1)))));

          }

          CodeTask.runTask(new ParalelTask(taskList));


            for (int i = 0; i < seconds(10); i++) {
                for (Gobject gobject :balls) {
                    ((Ball)gobject).update();
                }
                CodeTask.doStep();
            }

        }));
        cut();


    }

    public static void main(String[] args) {
        new GroupEx();
    }
}
