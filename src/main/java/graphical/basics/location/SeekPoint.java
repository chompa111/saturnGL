package graphical.basics.location;

import graphical.basics.location.Location;

public class SeekPoint implements Location {
    private double xOffset;
    private double yOffset;
    private Location seeking;

    public SeekPoint(double xOffset, double yOffset, Location seeking) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.seeking = seeking;
    }

    @Override
    public double getX() {
        return seeking.getX() + xOffset;
    }

    @Override
    public void setX(double x) {
        xOffset=(x-seeking.getX());
    }

    @Override
    public double getY() {
        return seeking.getY() + yOffset;
    }

    @Override
    public void setY(double y) {
        yOffset=(y-seeking.getY());
    }
}
