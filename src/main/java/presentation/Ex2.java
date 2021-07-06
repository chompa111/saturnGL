package presentation;

import codec.Presentation;
import graphical.basics.gobject.Ball;
import graphical.basics.location.Point;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.PositionTransform;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ex2 extends Presentation {
    @Override
    public void buildPresentation() {
        List<Ball> ballList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var ball=new Ball(new Point(100 + i * 0, 100 + i * 0), 0, 0, new Color(0,220,30));
            add(ball);
            ballList.add(ball);
        }

        List<Task> taskList= new ArrayList<>();

        for(int i=0;i<ballList.size();i++){
            taskList.add(new WaitTask(i*2+1).andThen(cicle(ballList.get(i),30)));
        }


        execute(new ParalelTask(taskList).repeat(2));
        cut();

    }

    Task cicle(Ball ball, int steps) {
        var location = ball.getRefereceLocations().get(0);
        var mesure = 1000 - (2 * location.getX());
        return new PositionTransform(ball, mesure, 0, steps)
                .andThen(new PositionTransform(ball, 0, mesure, steps))
                .andThen(new PositionTransform(ball, -mesure, 0, steps))
                .andThen(new PositionTransform(ball, 0, -mesure, steps));
    }

    public static void main(String[] args) {
        new Ex2().buildPresentation();
    }
}
