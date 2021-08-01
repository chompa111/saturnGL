package graphical.basics.task.transformation.value;

import graphical.basics.task.Task;
import graphical.basics.value.NumberHolder;

import java.util.Arrays;
import java.util.List;

public class ValueTransform implements Task {

    double change;
    List<NumberHolder> numberHolders;
    private int stepCount;
    private double a;
    private int steps;
    double delta;

    public ValueTransform(List<NumberHolder> numberHolders, double change, int steps) {
        this.steps = steps;
        this.numberHolders = numberHolders;
        this.change = change;
    }

    public ValueTransform(NumberHolder numberHolder, double change, int steps) {
        this.steps = steps;
        this.numberHolders = Arrays.asList(numberHolder);
        this.change = change;
    }

    @Override
    public void setup() {
        stepCount = 0;
        a = (4 * change) / (2 * steps + (steps * steps));
        delta = 0;
    }

    @Override
    public void afterStep() {

        if (stepCount < (steps / 2)) {
            delta += a;
            for (NumberHolder numberHolder : numberHolders) {
                numberHolder.add(delta);
            }
        } else {
            for (NumberHolder numberHolder : numberHolders) {
                numberHolder.add(delta);
            }
            delta -= a;
        }

        stepCount++;

    }

    @Override
    public boolean isDone() {
        return stepCount == steps;
    }
}
