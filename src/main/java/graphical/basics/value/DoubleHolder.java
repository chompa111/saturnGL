package graphical.basics.value;

import java.util.function.Supplier;

public class DoubleHolder implements NumberHolder {

    private double value;

    public DoubleHolder(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double x) {
        this.value = x;
    }

    @Override
    public Supplier<Double> getSupplier() {
        return () -> value;
    }

    @Override
    public void change(double x) {
        this.value += x;
    }


}
