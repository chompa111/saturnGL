package graphical.basics.gobject.shape;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;
import org.apache.batik.ext.awt.geom.PathLength;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class StrokeWriterV2 extends Gobject {

    ColorHolder strokeColor;
    Shape shape;

    double len;

    NumberHolder perc;

    public StrokeWriterV2(Shape shape, Color color) {
        this.shape = shape;
        this.strokeColor = new ColorHolder(color);

        len = new PathLength(shape).lengthOfPath();
        perc=new DoubleHolder(0);
    }

    @Override
    public void paint(Graphics g) {
        var g2d = ((Graphics2D) g);
        g.setColor(strokeColor.getColor());
        Stroke s = new BasicStroke(1.0f,                      // Width
                BasicStroke.CAP_SQUARE,    // End cap
                BasicStroke.JOIN_MITER,    // Join style
                10.0f,                     // Miter limit
                new float[]{(float) (perc.getValue() * len), (float) len}, // Dash pattern
                0.0f);
        g2d.setStroke(s);
        g2d.draw(shape);
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(strokeColor);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return null;
    }

    public NumberHolder getPerc() {
        return perc;
    }
}
