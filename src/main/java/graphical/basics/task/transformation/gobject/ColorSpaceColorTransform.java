package graphical.basics.task.transformation.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.task.Task;
import graphical.basics.task.TimeDefinedTask;

import java.awt.*;
import java.util.List;

public abstract class ColorSpaceColorTransform implements TimeDefinedTask {

    int steps;
    int stepCount;

    List<ColorHolder> colorHolders;

    Color color;
    float[][] aceleration;
    float[][] accumulation;
    float[][] delta;

    Gobject gobject;

    public ColorSpaceColorTransform(Gobject gobject, Color color, int steps) {
        this.color = color;
        this.steps = steps;
        this.gobject = gobject;
    }

    abstract float[] getColorComponents(Color color);

    abstract Color fromColorSpace(float[] colorComponents);

    @Override
    public void setup() {

        colorHolders = gobject.getColors();
        aceleration = new float[colorHolders.size()][4];
        accumulation = new float[colorHolders.size()][4];
        delta = new float[colorHolders.size()][4];
        stepCount = 0;

        float[] targetComponents = getColorComponents(color);

        for (int i = 0; i < aceleration.length; i++) {

            float[] colorComponents = getColorComponents(colorHolders.get(i).getColor());

            for (int j = 0; j < colorComponents.length; j++) {
                aceleration[i][j] = 4 * (targetComponents[j] - colorComponents[j] + 0.0f) / (2 * steps + (steps * steps));
                accumulation[i][j] = colorComponents[j];
            }

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
            colorHolders.get(i).setColor(fromColorSpace(accumulation[i]));
        }
        stepCount++;

    }

    @Override
    public boolean isDone() {
        return steps == stepCount;
    }

    @Override
    public Task forFrames(int frames) {
        steps = frames;
        return this;
    }
}
