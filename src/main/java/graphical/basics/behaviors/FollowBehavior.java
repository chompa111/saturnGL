package graphical.basics.behaviors;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;

public class FollowBehavior {
    public static Runnable follow(Gobject followedGobject, Gobject followerGobject) {
        return () -> followerGobject.setPositionTo(followedGobject.getMidPoint());
    }

    public static Runnable followWithDelay(Gobject followedGobject, Gobject followerGobject, double delay) {
        return () -> {
            var m1 = followerGobject.getMidPoint();
            var m2 = followedGobject.getMidPoint();

            followerGobject.changeSetPosition((m2.getX() - m1.getX()) * delay, (m2.getY() - m1.getY()) * delay);
        };
    }


    public static Runnable followWithDelay(Gobject followedGobject, Gobject followerGobject) {
        return followWithDelay(followedGobject, followerGobject, 0.1);
    }

    public static Runnable asSubtitle(Gobject followedGobject, Gobject followerGobject, double margin) {
        return () -> {
            var fd = followedGobject.getBorders();
            var fr = followerGobject.getBorders();
            var fdh2 = fd.getheight() / 2;
            var frh2 = fr.getheight() / 2;
            var fdmid = fd.midPoint();

            var desirredPoint = Location.at(fd.midPoint().getX(), fd.midPoint().getY() + fdh2 + frh2 + margin);

            followerGobject.setPositionTo(desirredPoint);
        };
    }

    public static Runnable asSubtitleWithDelay(Gobject followedGobject, Gobject followerGobject, double margin, double delay) {
        asSubtitle(followedGobject, followerGobject, margin).run();
        return () -> {
            var fd = followedGobject.getBorders();
            var fr = followerGobject.getBorders();
            var fdh2 = fd.getheight() / 2;
            var frh2 = fr.getheight() / 2;
            var fdmid = fd.midPoint();
            var frmid = fr.midPoint();

            var desirredPoint = Location.at(fdmid.getX(), fdmid.getY() + fdh2 + frh2 + margin);

            followerGobject.changeSetPosition((desirredPoint.getX() - frmid.getX()) * delay, (desirredPoint.getY() - frmid.getY()) * delay);
        };
    }


}
