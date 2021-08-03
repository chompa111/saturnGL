package graphical.basics.task.transformation.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.task.Task;

import java.awt.*;
import java.util.List;

public class ColorTranform2 implements Task {

    int steps;
    int stepCount;

    List<ColorHolder> colorHolders;

    Color color;
    double[][] aceleration;
    double[][] accumulation;
    double[][] delta;

    Gobject gobject;

    double factor;

    public ColorTranform2(Gobject gobject, Color color, double factor, int steps) {
        this.color = color;
        this.steps = steps;
        this.gobject = gobject;
        this.factor = factor;
    }


    @Override
    public void setup() {

        colorHolders = gobject.getColors();
        aceleration = new double[colorHolders.size()][4];
        accumulation = new double[colorHolders.size()][4];
        delta = new double[colorHolders.size()][4];
        stepCount = 0;


        for (int i = 0; i < aceleration.length; i++) {

            aceleration[i][0] = 4 * (color.getRed() - colorHolders.get(i).getColor().getRed() + 0.0) / (2 * factor * steps + (steps * factor * factor * steps));
            aceleration[i][1] = 4 * (color.getGreen() - colorHolders.get(i).getColor().getGreen() + 0.0) / (2 * factor * steps + (steps * factor * factor * steps));
            aceleration[i][2] = 4 * (color.getBlue() - colorHolders.get(i).getColor().getBlue() + 0.0) / (2 * factor * steps + (steps * factor * factor * steps));
            aceleration[i][3] = 4 * (color.getAlpha() - colorHolders.get(i).getColor().getAlpha() + 0.0) / (2 * factor * steps + (steps * factor * factor * steps));

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

    @Override
    public boolean isDone() {
        return steps == stepCount;
    }
}
