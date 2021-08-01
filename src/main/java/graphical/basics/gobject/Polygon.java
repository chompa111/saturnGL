package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon extends Gobject implements ShapeLike {

    public int[] xs;
    public int[] ys;

    ColorHolder colorHolder;

    List<Location> locations;


    public Polygon(double[] xs, double[] ys, Color color) {
        colorHolder = new ColorHolder(color);
        locations = new ArrayList<>();
        for (int i = 0; i < xs.length; i++) {
            locations.add(new Point(xs[i], ys[i]));
        }

        this.xs = new int[xs.length];
        this.ys = new int[xs.length];

    }

    public Polygon(Shape shape, ColorHolder colorHolder) {


        this.colorHolder = colorHolder;

        locations = new ArrayList<>();
        ArrayList<double[]> areaPoints = new ArrayList<double[]>();
        double[] coords = new double[6];

        for (PathIterator pi = new FlatteningPathIterator(shape.getPathIterator(null), 0.5); !pi.isDone(); pi.next()) {
            // The type will be SEG_LINETO, SEG_MOVETO, or SEG_CLOSE
            // Because the Area is composed of straight lines
            int type = pi.currentSegment(coords);
            // We record a double array of {segment type, x coord, y coord}
            double[] pathIteratorCoords = {type, coords[0], coords[1]};
            areaPoints.add(pathIteratorCoords);
        }

        double[] start = new double[3]; // To record where each polygon starts

        for (int i = 0; i < areaPoints.size(); i++) {
            // If we're not on the last point, return a line from this point to the next
            double[] currentElement = areaPoints.get(i);

            // We need a default value in case we've reached the end of the ArrayList
            double[] nextElement = {-1, -1, -1};
            if (i < areaPoints.size() - 1) {
                nextElement = areaPoints.get(i + 1);
            }

            // Make the lines
            if (currentElement[0] == PathIterator.SEG_MOVETO) {
                start = currentElement; // Record where the polygon started to close it later
            }

            if (nextElement[0] == PathIterator.SEG_LINETO) {

                locations.add(new Point(nextElement[1], nextElement[2]));

            } else if (nextElement[0] == PathIterator.SEG_CLOSE) {
                locations.add(new Point(start[1], start[2]));
            }

        }
        this.xs = new int[locations.size()];
        this.ys = new int[locations.size()];

    }

    public void addPoints(int amount) {
        for (int i = 0; i < amount; i++) {
            int randomIndex = (int) ((locations.size() - 1) * Math.random());
            locations.add(randomIndex + 1, Location.midPoint(locations.get(randomIndex), locations.get(randomIndex + 1)));
        }
        this.xs = new int[locations.size()];
        this.ys = new int[locations.size()];
    }

    public Polygon(List<Location> locations, Color color) {
        colorHolder = new ColorHolder(color);
        this.locations = locations;
        this.xs = new int[locations.size()];
        this.ys = new int[locations.size()];

    }

    public Polygon(Color color, Location... locations) {
        colorHolder = new ColorHolder(color);
        this.locations = Arrays.asList(locations);
        this.xs = new int[locations.length];
        this.ys = new int[locations.length];

    }

    public Polygon(Color color, double... path) {
        colorHolder = new ColorHolder(color);
        locations = new ArrayList<>();
        for (int i = 0; i < path.length; i += 2) {
            locations.add(new Point(path[i], path[i + 1]));
        }
        this.xs = new int[locations.size()];
        this.ys = new int[locations.size()];
    }

    public void updateShape() {
        for (int i = 0; i < xs.length; i++) {
            xs[i] = (int) locations.get(i).getX();
            ys[i] = (int) locations.get(i).getY();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(colorHolder.getColor());
        updateShape();
        g.drawPolygon(xs, ys, xs.length);
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(colorHolder);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return locations;
    }


    public int getNumOfEdges() {
        return locations.size();
    }

    @Override
    public Shape asShape() {
        updateShape();
        return new java.awt.Polygon(xs, ys, xs.length);
    }
}
