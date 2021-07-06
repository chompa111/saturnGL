package graphical.basics.location;

import java.util.List;

//defines a render region
public class LocationPair {

    Location l1;
    Location l2;

    public LocationPair(Location l1, Location l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    public Location getL1() {
        return l1;
    }

    public void setL1(Location l1) {
        this.l1 = l1;
    }

    public Location getL2() {
        return l2;
    }

    public void setL2(Location l2) {
        this.l2 = l2;
    }

    public Location midPoint() {
        return new Point((l1.getX() + l2.getX()) / 2, (l1.getY() + l2.getY()) / 2);
    }


    public LocationPair(List<LocationPair> pairs) {
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;

        for (LocationPair locationPair : pairs) {
            var l1 = locationPair.getL1();
            var l2 = locationPair.getL2();
            //MAX
            if (l1.getX() > maxX) {
                maxX = l1.getX();
            }
            if (l2.getX() > maxX) {
                maxX = l2.getX();
            }

            if (l1.getY() > maxY) {
                maxY = l1.getY();
            }
            if (l2.getY() > maxY) {
                maxY = l2.getY();
            }

            //MIN
            if (l1.getX() < minX) {
                minX = l1.getX();
            }
            if (l2.getX() < minX) {
                minX = l2.getX();
            }

            if (l1.getY() < minY) {
                minY = l1.getY();
            }
            if (l2.getY() < minY) {
                minY = l2.getY();
            }
        }

        l1 = new Point(minX, minY);
        l2 = new Point(maxX, maxY);
    }
}
