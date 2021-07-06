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

public class BubbleEx extends Presentation {
    @Override
    public void buildPresentation() {
        List<Ball> balls = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            balls.add(new Ball(new Point(300 + 50 * i, 500), new Color((i + 1) * 20, 60, 0)));
            add(balls.get(i));
        }

        execute(new WaitTask(seconds(1)));

        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * balls.size());
            int random2 = (int) (Math.random() * balls.size());
            if (random != random2) {
                var b1=balls.get(random);
                var b2=balls.get(random2);
                execute(changeBalls(b1, b2,seconds(1)));
                var removed1 = balls.remove(random);
                balls.add(random, b2);
                var removed2 = balls.remove(random2);
                balls.add(random2, b1);


            }

        }

        for (int i = 0; i < balls.size() - 1; i++) {
            var b1 = balls.get(i);
            var b2 = balls.get(i + 1);

            var t1 = new PositionTransform(b1, 0, -50, seconds(1));
            var t2 = new PositionTransform(b2, 0, -50, seconds(1));

            execute(t1.parallel(t2).wait(seconds(1)));
            if (b1.getColors().get(0).getColor().getRed() > b2.getColors().get(0).getColor().getRed()) {
                execute(changeBalls(b1, b2,seconds(1)));

                balls.remove(i);
                balls.add(i, b2);
                balls.remove(i + 1);
                balls.add(i + 1, b1);
            }

            var t3 = new PositionTransform(b1, 0, 50, seconds(1));
            var t4 = new PositionTransform(b2, 0, 50, seconds(1));
            execute(t3,t4);


        }

        boolean chenged=true;

        while (chenged){
            chenged=false;
            for (int i = 0; i < balls.size() - 1; i++) {
                var b1 = balls.get(i);
                var b2 = balls.get(i + 1);

                var t1 = new PositionTransform(b1, 0, -50, seconds(0.5));
                var t2 = new PositionTransform(b2, 0, -50, seconds(0.5));

                execute(t1.parallel(t2).wait(seconds(0.5)));
                if (b1.getColors().get(0).getColor().getRed() > b2.getColors().get(0).getColor().getRed()) {
                   chenged=true;
                    execute(changeBalls(b1, b2,seconds(0.5)));

                    balls.remove(i);
                    balls.add(i, b2);
                    balls.remove(i + 1);
                    balls.add(i + 1, b1);
                }

                var t3 = new PositionTransform(b1, 0, 50, seconds(0.5));
                var t4 = new PositionTransform(b2, 0, 50, seconds(0.5));
                execute(t3,t4);


            }
        }




        cut();

    }

    Task changeBalls(Ball b1, Ball b2,int frames) {
        var t1 = new PositionTransform(b1, b2.getRefereceLocations().get(0).getX() - b1.getRefereceLocations().get(0).getX(), 0, frames);
        var t2 = new PositionTransform(b2, b1.getRefereceLocations().get(0).getX() - b2.getRefereceLocations().get(0).getX(), 0, frames);

        return new ParalelTask(t1, t2);
    }

    public static void main(String[] args) {
        new BubbleEx().buildPresentation();
    }
}
