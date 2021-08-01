package graphical.basics.value;

import java.util.function.Supplier;

public class BindedNumberHolder implements NumberHolder {

    double offset = 0;

    Supplier<Double> supplier;

    public BindedNumberHolder(double offset, Supplier<Double> supplier) {
        this.offset = offset;
        this.supplier = supplier;
    }

    public BindedNumberHolder(Supplier<Double> supplier) {
        this.supplier = supplier;
    }

    @Override
    public double getValue() {
        return supplier.get() + offset;
    }

    @Override
    public void setValue(double x) {
        offset = (x - supplier.get());
    }

    @Override
    public Supplier<Double> getSupplier() {
        return () -> supplier.get() + offset;
    }

    @Override
    public void add(double x) {
        offset += x;
    }
}
