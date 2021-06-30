package graphical.basics.gobject.latex;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

public class Char extends Gobject {

    Font font;
    Location location;

    int size;

    ColorHolder color;

    char[] c = new char[1];
    AffineTransform affineTransform;

    public Char(Font font, Location location, char[] c, int size, Color color) {
        this.color = new ColorHolder(color);
        this.size = size;
        this.font = font;
        this.location = location;
        this.c = c;
    }

    @Override
    public void paint(Graphics g) {
        var t = ((Graphics2D) g).getTransform();
        g.setColor(color.getColor());
        g.setFont(new Font(font.getFontName(), font.getStyle(), size));
        g.drawChars(c, 0, 1, (int) location.getX(), (int) location.getY());
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(color);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(location);
    }
}
