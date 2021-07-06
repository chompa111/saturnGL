package graphical.basics.task.transformation.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Gobject;
import graphical.basics.gobject.Group;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColorTranform implements Task {

    int steps;
    int stepCount;

    List<ColorHolder> colorHolders;

    Color color;
    double[][] aceleration;
    double[][] accumulation;
    double[][] delta;

    Gobject gobject;

    public ColorTranform(Gobject gobject, Color color, int steps) {
        this.color = color;
        this.steps = steps;
        this.gobject = gobject;
    }


    @Override
    public void setup() {

        colorHolders = gobject.getColors();
        aceleration = new double[colorHolders.size()][4];
        accumulation = new double[colorHolders.size()][4];
        delta = new double[colorHolders.size()][4];
        stepCount = 0;


        for (int i = 0; i < aceleration.length; i++) {

            aceleration[i][0] = 4 * (color.getRed() - colorHolders.get(i).getColor().getRed() + 0.0) / (2 * steps + (steps * steps));
            aceleration[i][1] = 4 * (color.getGreen() - colorHolders.get(i).getColor().getGreen() + 0.0) / (2 * steps + (steps * steps));
            aceleration[i][2] = 4 * (color.getBlue() - colorHolders.get(i).getColor().getBlue() + 0.0) / (2 * steps + (steps * steps));
            aceleration[i][3] = 4 * (color.getAlpha() - colorHolders.get(i).getColor().getAlpha() + 0.0) / (2 * steps + (steps * steps));

            accumulation[i][0] = colorHolders.get(i).getColor().getRed();
            accumulation[i][1] = colorHolders.get(i).getColor().getGreen();
            accumulation[i][2] = colorHolders.get(i).getColor().getBlue();
            accumulation[i][3] = colorHolders.get(i).getColor().getAlpha();

        }

    }

    @Override
    public void step() {
        if (stepCount < steps / 2) {
            for (int i = 0; i < aceleration.length; i++) {
                for (int j = 0; j < aceleration[i].length; j++) {
                    delta[i][j] += aceleration[i][j];
                    accumulation[i][j] += delta[i][j];

                }
            }
        } else {
            for (int i = 0; i < aceleration.length; i++) {
                for (int j = 0; j < aceleration[i].length; j++) {
                    accumulation[i][j] += delta[i][j];
                    delta[i][j] -= aceleration[i][j];
                }
            }
        }


        for (int i = 0; i < aceleration.length; i++) {
            colorHolders.get(i).setColor(new Color((int) accumulation[i][0], (int) accumulation[i][1], (int) accumulation[i][2], (int) accumulation[i][3]));
        }
        stepCount++;

    }

    public static Task delaYad(Gobject gobject, Color color, int steps, int delay) {
        List<Task> tasks = new ArrayList<>();
        if (gobject instanceof Group) {
            int i=1;
            for (Gobject go : ((Group) gobject).getGobjects()) {
                tasks.add(new WaitTask(i*delay).andThen(new ColorTranform(go, color, steps)));
                i++;
            }
            return new ParalelTask(tasks);
        }
        return null;
    }

    @Override
    public boolean isDone() {
        return steps == stepCount;
    }
}
