package graphical.basics.task.transformation.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.task.Task;

import java.awt.*;
import java.util.List;

public class ColorListTranform2 implements Task {

    int steps;
    int stepCount;

    List<ColorHolder> fromColors;
    List<Color> toColors;

    double[][] aceleration;
    double[][] accumulation;
    double[][] delta;

    double factor;

    public ColorListTranform2(List<ColorHolder> fromColors, List<Color> toColors,double factor, int steps) {
        this.fromColors = fromColors;
        this.toColors = toColors;
        this.steps = steps;
        this.factor=factor;
    }


    @Override
    public void setup() {


        aceleration = new double[fromColors.size()][4];
        accumulation = new double[fromColors.size()][4];
        delta = new double[fromColors.size()][4];
        stepCount = 0;


        for (int i = 0; i < aceleration.length; i++) {

            aceleration[i][0] = 4 * (toColors.get(i).getRed() - fromColors.get(i).getColor().getRed() + 0.0) / (2 *factor *steps + (steps*factor*factor* steps));
            aceleration[i][1] = 4 * (toColors.get(i).getGreen() - fromColors.get(i).getColor().getGreen() + 0.0) / (2 *factor *steps + (steps*factor*factor* steps));
            aceleration[i][2] = 4 * (toColors.get(i).getBlue() - fromColors.get(i).getColor().getBlue() + 0.0) / (2 *factor *steps + (steps*factor*factor* steps));
            aceleration[i][3] = 4 * (toColors.get(i).getAlpha() - fromColors.get(i).getColor().getAlpha() + 0.0) / (2 *factor *steps + (steps*factor*factor* steps));

            accumulation[i][0] = fromColors.get(i).getColor().getRed();
            accumulation[i][1] = fromColors.get(i).getColor().getGreen();
            accumulation[i][2] = fromColors.get(i).getColor().getBlue();
            accumulation[i][3] = fromColors.get(i).getColor().getAlpha();

        }

    }

    @Override
    public void afterStep() {
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
            fromColors.get(i).setColor(new Color((int) accumulation[i][0], (int) accumulation[i][1], (int) accumulation[i][2], (int) accumulation[i][3]));
        }
        stepCount++;

    }


    @Override
    public boolean isDone() {
        return steps == stepCount;
    }
}
