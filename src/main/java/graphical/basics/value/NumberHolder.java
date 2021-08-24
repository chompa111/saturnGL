package graphical.basics.value;

import graphical.basics.presentation.Presentation;
import graphical.basics.task.Task;
import graphical.basics.task.transformation.value.ConstantSpeedTransformation;
import graphical.basics.task.transformation.value.MeanSpeedTransformation;
import graphical.basics.task.transformation.value.ValueTransform;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

public interface NumberHolder {

    double getValue();

    void setValue(double x);

    Supplier<Double> getSupplier();

    void add(double x);


    default Task change(double amount, int steps) {
        return new ValueTransform(this, amount, steps);
    }

    default Task change(double amount) {
        return change(amount, Presentation.staticReference.seconds(1));
    }

    default Task change(double amount, int steps, ChangeType changeType) {
        switch (changeType) {
            case ACELERATED:
                return change(amount, steps);
            case MEAN_SPEED:
                return new MeanSpeedTransformation(Collections.singletonList(this), amount, steps);
            case CONSTANT_SPEED:
                return new ConstantSpeedTransformation(this, amount, steps);
        }
        return null;
    }


}
