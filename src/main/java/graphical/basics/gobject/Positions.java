package graphical.basics.gobject;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;

import java.time.LocalDate;

public class Positions {
    public static Location rightOf(Gobject g1, Gobject g2) {
        var g1Borders = g1.getBorders();
        var g2Borders = g2.getBorders();

        return g1Borders.midPoint().plus(g1Borders.getwidth() / 2, 0).plus(g2Borders.getwidth() / 2, 0);
    }


    public static double srqtR(double x, double e, int lvls) {
        if (lvls <= 0) return e;
        var extimation = srqtR(x, e, lvls - 1);
        return (x + extimation * extimation) / (2 * extimation);
    }

    public static void main(String[] args) {
        System.out.println(srqtR(-1,2,1003));
    }
}
