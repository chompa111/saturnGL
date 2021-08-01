package graphical.basics.task.transformation.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.awt.*;
import java.util.List;

public class Pepe  extends Gobject {
    @Override
    public void paint(Graphics g) {
        TeXFormula a = new TeXFormula("\\left(\\sqrt{\\frac{\\frac{x}{y}}{a^2^2}}\\right)^{\\frac{1}{2}}(a)");
        var icon2 = a.createTeXIcon(TeXConstants.STYLE_TEXT, 50);
        icon2.paintIcon(null, g, 400, 500);
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return null;
    }

    @Override
    public List<Location> getRefereceLocations() {
        return null;
    }
}
