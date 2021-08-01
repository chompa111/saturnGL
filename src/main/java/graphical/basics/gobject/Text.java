package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.latex.Char;
import graphical.basics.gobject.struct.Char2;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.List;

public class Text extends Gobject {

    List<Char2> charList;

    public static List<Gobject> generateText(Font font, String s, Location location, Color color) {

        List<Gobject> gobjects= new ArrayList<>();
        var gv = font.createGlyphVector(new FontRenderContext(null, true, true), s);

        for (int i = 0; i < s.length(); i++) {
            var sh = gv.getGlyphOutline(i);
            var c = s.charAt(i);
            gobjects.add(new Char2(font, new Point(sh.getBounds().x, sh.getBounds().y), new char[]{c}, 2, color));
        }

        return gobjects;

    }

    @Override
    public void paint(Graphics g) {

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
