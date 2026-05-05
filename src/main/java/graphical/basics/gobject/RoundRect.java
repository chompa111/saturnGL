package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.FillAndStroke;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.StrokeGobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class RoundRect extends FillAndStroke implements ShapeLike {

    private final Location corner;
    private final NumberHolder width;
    private final NumberHolder height;
    private final NumberHolder roundness = new DoubleHolder(0);

    RoundRectangle2D awtRect = new RoundRectangle2D.Double();

    public RoundRect(Location corner, NumberHolder width, NumberHolder height, Color color) {
        this.corner = corner;
        this.width = width;
        this.height = height;
        fillColorHolder = new ColorHolder(color);
    }

    @Override
    public Shape asShape() {
        var min = Math.min(width.getValue(),height.getValue());
        var shape = new RoundRectangle2D.Double(corner.getX(), corner.getY(), width.getValue(), height.getValue(), min * roundness.getValue(), min * roundness.getValue());
        return getTranformation().createTransformedShape(shape);
    }

    private void paintStrokeColor(final Graphics2D g) {
        if (strokeColorHolder != null) {
            g.setStroke(getStroke().getStroke());
            g.setColor(strokeColorHolder.getColor());
            g.draw(awtRect);
        }
    }

    private void paintFillColor(final Graphics2D g) {
        if (fillColorHolder != null) {
            g.setColor(fillColorHolder.getColor());
            g.fill(awtRect);
        }
    }

    @Override
    public void paint(final Graphics g) {
        var min = Math.min(width.getValue(),height.getValue());
        awtRect.setRoundRect(corner.getX(), corner.getY(), width.getValue(), height.getValue(), min* roundness.getValue(), min * roundness.getValue());
        final var g2d = (Graphics2D) g;
        paintFillColor(g2d);
        paintStrokeColor(g2d);
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(corner, Location.at(corner.getX() + width.getValue(), corner.getY() + height.getValue()));
    }

    @Override
    public List<Location> getReferenceLocations() {
        return List.of(corner);
    }

    public NumberHolder getRoundness() {
        return roundness;
    }

    public NumberHolder getWidthValue() {
        return width;
    }

    public NumberHolder getHeightValue() {
        return height;
    }
}
