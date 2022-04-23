package graphical.basics.gobject.latex.lixao;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.latex.GraphicsdrawCharProxy;
import graphical.basics.location.Location;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Latex {


    public static List<Gobject> generateExp(String s, Location location, Color color) {
        return generateExp(s, location, color, 50);
    }

    public static List<Gobject> generateExp(String s, Location location, Color color, double size) {

        TeXFormula a = new TeXFormula(s);
        var icon2 = a.createTeXIcon(TeXConstants.STYLE_DISPLAY, (float) size);
        var img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        var fakeGraphics = new GraphicsdrawCharProxy((Graphics2D) img.getGraphics(), color);
        icon2.paintIcon(null, fakeGraphics, (int) location.getX(), (int) location.getY());

        return fakeGraphics.getCharList();
    }


    public static List<Gobject> generateExp(String s, double x, double y, Color color) {

        TeXFormula a = new TeXFormula(s);
        var icon2 = a.createTeXIcon(TeXConstants.STYLE_TEXT, 50);
        var img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        var fakeGraphics = new GraphicsdrawCharProxy((Graphics2D) img.getGraphics(), color);
        icon2.paintIcon(null, fakeGraphics, (int) x, (int) y);

        return fakeGraphics.getCharList();
    }
}
