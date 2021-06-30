package graphical.basics.value;

import java.util.function.Supplier;

public interface NumberHolder {

    double getValue();

    void setValue(double x);

    Supplier<Double> getSupplier();

    void change(double x);


}
