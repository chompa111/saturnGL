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
}
