package graphical.basics.gobject.latex;

import static java.awt.image.BufferedImage.*;
import static org.scilab.forge.jlatexmath.TeXConstants.*;

import graphical.basics.gobject.Group;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Latex {

    private Latex() {
    }

    public static Group of(String s) {
        return new Group(generateExp(s, Location.at(0, 0), Color.white));
    }

    public static Group of(String s, double size) {
        return new Group(generateExp(s, Location.at(0, 0), Color.white, size));
    }

    private static final double FONT_SIZE = 50;

    public static List<Gobject> generateExp(String s, Location location, Color color) {
        return generateExp(s, location, color, FONT_SIZE);
    }


    public static List<Gobject> generateExp(String s, Location location, Color color, double size) {

        final var formula = new TeXFormula(s);
        var icon2 = formula.createTeXIcon(STYLE_DISPLAY, (float) size);
        var img = new BufferedImage(1, 1, TYPE_INT_ARGB);
        var fakeGraphics = new GraphicsDrawCharProxy((Graphics2D) img.getGraphics(), color);
        icon2.paintIcon(null, fakeGraphics, (int) location.getX(), (int) location.getY());

        return fakeGraphics.getCharList();
    }


    public static List<Gobject> generateExp(String s, double x, double y, Color color) {

        TeXFormula a = new TeXFormula(s);
        var icon2 = a.createTeXIcon(STYLE_TEXT, 50);
        var img = new BufferedImage(1, 1, TYPE_INT_ARGB);
        var fakeGraphics = new GraphicsDrawCharProxy((Graphics2D) img.getGraphics(), color);
        icon2.paintIcon(null, fakeGraphics, (int) x, (int) y);

        return fakeGraphics.getCharList();
    }
}
