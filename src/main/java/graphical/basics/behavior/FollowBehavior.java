package graphical.basics.behavior;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;

public class FollowBehavior implements Behavior {
    Gobject gobject;
    Location location;

    double oldX;
    double oldY;

    public FollowBehavior(Gobject gobject, Location location) {
        this.gobject = gobject;
        this.location = location;
        oldX = location.getX();
        oldY = location.getY();
    }

    @Override
    public void update() {

        var deltaX = location.getX() - oldX;
        var deltaY = location.getY() - oldY;

        gobject.changeSetPosition(deltaX, deltaY);

        oldX = location.getX();
        oldY = location.getY();

    }
}
