package graphical.basics.gobject.struct;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.value.DoubleHolder;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Char2 extends ShapeGobject2 {

    char[] c;

    Font font;
    int size;

    public Char2(Font font, Location location, char[] c, int size, Color color) {
        this.fillColorHolder = new ColorHolder(color);
        this.size = size;
        this.font = font;
        var t = new AffineTransform();

        t.translate(location.getX(), location.getY());
        t.scale(size / 100.0, size / 100.0);
        t.translate(-(location.getX()), -(location.getY()));

        //this.scale=new DoubleHolder(size/100.0);

        var pepe = font.createGlyphVector(new FontRenderContext(null, true, true), "" + c[0]);
        this.shape = t.createTransformedShape(pepe.getOutline((int) location.getX(), (int) location.getY()));
        var bounds = shape.getBounds();
        shapeOfsetX = location.getX();
        shapeOfsetY =location.getY();
        this.location=location;
        this.c = c;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
