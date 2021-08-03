package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class StringGobject extends Gobject implements ShapeLike {

    Font font=new Font(Font.DIALOG_INPUT, Font.BOLD, 30);

    Location location;
    ColorHolder colorHolder;
    Supplier<String> string;

    public StringGobject(Location location, Font font, Supplier<String> string, Color color) {
        this.location = location;
        this.font = font;
        this.colorHolder = new ColorHolder(color);
        this.string = string;
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(font);
        g.setColor(colorHolder.getColor());
        g.drawString(string.get(), (int) location.getX(), (int) location.getY());
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
        return Arrays.asList(colorHolder);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(location);
    }

    @Override
    public Shape asShape() {
        var gv = font.createGlyphVector(new FontRenderContext(null, true, true), string.get());
        var t = new AffineTransform();
        t.translate(location.getX(), location.getY());
        return t.createTransformedShape(gv.getOutline());
    }

}
