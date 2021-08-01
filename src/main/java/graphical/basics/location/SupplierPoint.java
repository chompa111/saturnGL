package graphical.basics.location;

import graphical.basics.location.Location;

import java.util.function.Supplier;

public class SupplierPoint implements Location {
    private double xOffset;
    private double yOffset;
    Supplier<Double> xSupplier;
    Supplier<Double> ySupplier;


    @Override
    public double getX() {
        return xSupplier.get()+xOffset;
    }

    @Override
    public void setX(double x) {
        //xOffset=(x-xSupplier.get());
    }

    @Override
    public double getY() {
        return ySupplier.get()+yOffset;
    }

    @Override
    public void setY(double y) {
        //yOffset=(y-ySupplier.get());
    }

    public SupplierPoint(double xOffset, double yOffset, Supplier<Double> xSupplier, Supplier<Double> ySupplier) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
    }

    public SupplierPoint( Supplier<Double> xSupplier, Supplier<Double> ySupplier) {
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
    }
}
