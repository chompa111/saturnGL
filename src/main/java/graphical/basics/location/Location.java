package graphical.basics.location;

import graphical.basics.gobject.struct.Gobject;

import java.awt.geom.Point2D;
import java.util.function.Supplier;

public interface Location {


    double getX();

    void setX(double y);

    double getY();

    void setY(double y);

    default void incrementX(double amount) {
        this.setX(this.getX() + amount);
    }

    default void incrementY(double amount) {
        this.setY(this.getY() + amount);
    }

    static Point midPoint(Location a, Location b) {
        return new Point((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
    }

    static SupplierPoint spyMidPoint(Location a, Location b) {
        return new SupplierPoint(() -> ((a.getX() + b.getX()) / 2), () -> ((a.getY() + b.getY()) / 2));
    }

    public default double distanceTo(Location location) {
        return Math.sqrt((this.getX() - location.getX()) * (this.getX() - location.getX()) + (this.getY() - location.getY()) * (this.getY() - location.getY()));
    }

    static Location at(double x, double y) {
        return new Point(x, y);
    }

    default Location copy() {
        return Location.at(this.getX(), this.getY());
    }

    static Location getTransformedObservedLocation(Location location, Gobject gobject) {

        Supplier<Point2D> point2dGetter = () -> {
            var af = gobject.getTranformation();
            return af.transform(new Point2D.Double(location.getX(), location.getY()), null);
        };

        return new SupplierPoint(() -> point2dGetter.get().getX(), () -> point2dGetter.get().getY());
    }

}
