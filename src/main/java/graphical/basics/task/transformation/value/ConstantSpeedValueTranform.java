package graphical.basics.task.transformation.value;

import graphical.basics.task.Task;
import graphical.basics.value.NumberHolder;

import java.util.List;

public class ConstantSpeedValueTranform implements Task {
    double change;
    List<NumberHolder> numberHolders;
    private int stepCount;
    private final int steps;
    private final double stepAmount;

    public ConstantSpeedValueTranform(List<NumberHolder> numberHolders, double change, int steps) {
        this.steps = steps;
        this.numberHolders = numberHolders;
        this.change = change;
        stepAmount = change / steps + 0.0;
    }

    @Override
    public void setup() {
        //nothing
    }

    @Override
    public void step() {
        for (NumberHolder numberHolder : numberHolders) {
            numberHolder.add(stepAmount);
        }
        stepCount++;
    }

    @Override
    public boolean isDone() {
        return stepCount == steps;
    }
}
