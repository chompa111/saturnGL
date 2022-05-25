package graphical.basics.gobject.latex;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.FillAndStroke;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

public class Rect extends FillAndStroke implements ShapeLike {

    private final Location upperLeftDiagonal;
    private final Location lowerRightDiagonal;

    private final Rectangle2D.Double awtRect = new Rectangle2D.Double();

    public Rect(final Location upperLeftDiagonal, final Location lowerRightDiagonal, final Color color) {
        this.upperLeftDiagonal = upperLeftDiagonal;
        this.lowerRightDiagonal = lowerRightDiagonal;
        fillColorHolder = new ColorHolder(color);
    }

    @Override
    public void paint(final Graphics g) {
        awtRect.setRect(upperLeftDiagonal.getX(), upperLeftDiagonal.getY(), getCurrentWidth(), getCurrentHeight());
        final var g2d = (Graphics2D) g;
        paintFillColor(g2d);
        paintStrokeColor(g2d);
    }

    private void paintStrokeColor(final Graphics2D g) {
        if (strokeColorHolder != null) {
            g.setStroke(new BasicStroke((float) getStrokeThickness().getValue()));
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
    public LocationPair getBorders() {
        return new LocationPair(upperLeftDiagonal, lowerRightDiagonal);
    }


    @Override
    public List<Location> getReferenceLocations() {
        return Arrays.asList(upperLeftDiagonal, lowerRightDiagonal);
    }

    @Override
    public Shape asShape() {
        var rect = new Rectangle((int) upperLeftDiagonal.getX(),
                (int) upperLeftDiagonal.getY(),
                (int) getCurrentWidth(),
                (int) getCurrentHeight());

        return getTranformation().createTransformedShape(rect);
    }

    private double getCurrentWidth() {
        return lowerRightDiagonal.getX() - upperLeftDiagonal.getX();
    }

    private double getCurrentHeight() {
        return lowerRightDiagonal.getY() - upperLeftDiagonal.getY();
    }

    public Location getUpperLeftDiagonal() { return upperLeftDiagonal; }

    public Location getLowerRightDiagonal() {
        return lowerRightDiagonal;
    }
}
