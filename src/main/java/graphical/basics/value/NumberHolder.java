package graphical.basics.value;

import graphical.basics.presentation.Presentation;
import graphical.basics.task.Task;
import graphical.basics.task.transformation.value.ValueTransform;

import java.util.function.Supplier;

public interface NumberHolder {

    double getValue();

    void setValue(double x);

    Supplier<Double> getSupplier();

    void change(double x);


    default Task aceleratedChange(double amount, int steps) {
        return new ValueTransform(this, amount, steps);
    }

    default Task aceleratedChange(double amount) {
        return aceleratedChange(amount, Presentation.staticReference.seconds(1));
    }


}
