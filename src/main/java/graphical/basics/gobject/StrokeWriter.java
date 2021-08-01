package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

public class StrokeWriter extends Gobject {

    ArrayList<Line> lines = new ArrayList<>();

    ArrayList<Double> weights = new ArrayList<>();

    NumberHolder numberHolder;

    public StrokeWriter(Shape shape, NumberHolder numberHolder,Color color) {

        this.numberHolder = numberHolder;
        ArrayList<double[]> areaPoints = new ArrayList<double[]>();

        double[] coords = new double[6];

        for (PathIterator pi = new FlatteningPathIterator(shape.getPathIterator(null), 1); !pi.isDone(); pi.next()) {
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
                lines.add(
                        new Line(
                                new Point(currentElement[1], currentElement[2]),
                                new Point(nextElement[1], nextElement[2]), color
                        )
                );
            } else if (nextElement[0] == PathIterator.SEG_CLOSE) {
                lines.add(
                        new Line(
                                new Point(currentElement[1], currentElement[2]),
                                new Point(start[1], start[2]), color
                        )
                );
            }

        }
        var total = 0.0;
        for (Line line : lines) {
            total += line.lenght();
        }

        for (Line line : lines) {
            weights.add(line.lenght() / total);
        }
    }

    public StrokeWriter(Shape shape, Location location, NumberHolder numberHolder) {
        this.numberHolder = numberHolder;
        ArrayList<double[]> areaPoints = new ArrayList<double[]>();

        double[] coords = new double[6];

        for (PathIterator pi = new FlatteningPathIterator(shape.getPathIterator(null), 1); !pi.isDone(); pi.next()) {
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
                lines.add(
                        new Line(
                                new Point(currentElement[1] + location.getX(), currentElement[2] + location.getY()),
                                new Point(nextElement[1] + location.getX(), nextElement[2] + location.getY()), Color.white
                        )
                );
            } else if (nextElement[0] == PathIterator.SEG_CLOSE) {
                lines.add(
                        new Line(
                                new Point(currentElement[1] + location.getX(), currentElement[2] + location.getY()),
                                new Point(start[1] + location.getX(), start[2] + location.getY()), Color.white
                        )
                );
            }

        }
        var total = 0.0;
        for (Line line : lines) {
            total += line.lenght();
        }

        for (Line line : lines) {
            weights.add(line.lenght() / total);
        }
    }

    @Override
    public void paint(Graphics g) {
        var g2d= (Graphics2D)g;


        var totalperc = 0.0;
        var goal = numberHolder.getValue();
        for (int i = 0; i < lines.size(); i++) {
            if (totalperc >= goal) break;

            var delta = goal - totalperc;
            if (weights.get(i) >= delta) {
                lines.get(i).paint(g, delta / weights.get(i));
                totalperc = goal;
            } else {
                totalperc += weights.get(i);
                lines.get(i).paint(g);
            }
        }
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        var list = new ArrayList<ColorHolder>();
        for (Line line : lines) {
            list.addAll(line.getColors());
        }
        return list;
    }

    @Override
    public List<Location> getRefereceLocations() {
        var list = new ArrayList<Location>();
        for (Line line : lines) {
            list.addAll(line.getRefereceLocations());
        }
        return list;
    }
}
