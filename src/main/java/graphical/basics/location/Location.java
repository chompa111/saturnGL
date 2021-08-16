package graphical.basics.location;

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
        return new SupplierPoint(()->((a.getX() + b.getX()) / 2), ()->((a.getY() + b.getY()) / 2));
    }

}
