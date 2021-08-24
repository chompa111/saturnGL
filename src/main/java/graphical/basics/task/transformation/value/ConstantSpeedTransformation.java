package graphical.basics.task.transformation.value;

import graphical.basics.task.Task;
import graphical.basics.value.NumberHolder;

import java.util.List;

public class ConstantSpeedTransformation implements Task {

    double speed;
    NumberHolder numberHolder;
    private int stepCount;
    private final int steps;

    public ConstantSpeedTransformation(NumberHolder numberHolders, double change, int steps) {
        this.steps = steps;
        this.numberHolder = numberHolders;
        this.speed = change;
    }

    @Override
    public void setup() {
        stepCount=0;
    }

    @Override
    public void step() {
        numberHolder.add(speed);
        stepCount++;
    }

    @Override
    public boolean isDone() {
        return steps == stepCount;
    }
}
