package graphical.basics.gobject.latex;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

public class Char extends Gobject implements ShapeLike {

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
        var shapeLike=asShape();
        ((Graphics2D) g).fill(shapeLike);
    }

    @Override
    public LocationPair getBorders() {
        var bounds = asShape().getBounds();
        double x = bounds.getBounds().getX();
        double y = bounds.getY();
        return new LocationPair(new graphical.basics.location.Point(x, y), new Point(x + bounds.getWidth(), y + bounds.getHeight()));
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(color);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(location);
    }

    public Font getFont() {
        return font;
    }

    @Override
    public Shape asShape() {
        var t = new AffineTransform();

        t.translate(location.getX(), location.getY());
        t.scale(size / 100.0, size / 100.0);
        t.translate(-(location.getX()), -(location.getY()));
        var pepe = getFont().createGlyphVector(new FontRenderContext(null, true, true), "" + c[0]);

        return t.createTransformedShape(pepe.getOutline((int) location.getX(), (int) location.getY()));
    }
}
