package graphical.basics.behavior;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;

public class FollowBehavior implements Behavior {
    private final Gobject gobject;
    private final Location location;

    private double oldX;
    private double oldY;

    public FollowBehavior(final Gobject gobject, final Location location) {
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
