package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.FillAndStroke;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DynamicPath extends FillAndStroke {

    int widingRule = 0;

    public List<Location> vertices;

    public DynamicPath(Shape shape, ColorHolder fill, ColorHolder stroke) {
        super(fill, stroke);
        vertices = locationsOfFlatShape(shape);
        widingRule = shape.getPathIterator(null).getWindingRule();
    }

    @Override
    public void paint(Graphics g) {



        var shape = momentShape();
        var g2d = ((Graphics2D) g);


        if (this.fillColorHolder != null) {
            g.setColor(fillColorHolder.getColor());
            g2d.fill(shape);
        }
        if (strokeColorHolder != null) {
            g.setColor(strokeColorHolder.getColor());
            g2d.draw(shape);
        }

        g.setColor(Color.green);
        for(int i=0;i<vertices.size();i++){
            var l= vertices.get(i);
            g.drawString(i+"",(int)l.getX(),(int)l.getY());
            g.fillOval((int)l.getX()-2,(int)l.getY()-2,4,4);
        }

    }

    @Override
    public LocationPair getBorders() {
        var bounds = momentShape().getBounds();
        double x = bounds.getBounds().getX();
        double y = bounds.getY();
        return new LocationPair(new graphical.basics.location.Point(x, y), new Point(x + bounds.getWidth(), y + bounds.getHeight()));
    }

    @Override
    public List<Location> getRefereceLocations() {
        return this.vertices;
    }


    private Shape momentShape() {
        GeneralPath generalPath = new GeneralPath();
        generalPath.setWindingRule(widingRule);
        Set<Location> visitedPositions = new HashSet<>();
        boolean next = false;
        generalPath.moveTo(vertices.get(0).getX(), vertices.get(0).getY());
        for (int i = 1; i < vertices.size(); i++) {
            var vertex = vertices.get(i);
            if (visitedPositions.contains(vertex)) {
                next = true;
                generalPath.lineTo(vertex.getX(), vertex.getY());
            } else {
                if (next) {
                    generalPath.moveTo(vertex.getX(), vertex.getY());
                    next = false;
                } else {
                    generalPath.lineTo(vertex.getX(), vertex.getY());
                }

            }


            visitedPositions.add(vertex);
        }

        generalPath.closePath();

        return generalPath;
    }


    private List<Location> locationsOfFlatShape(Shape shape) {
        var locations = new ArrayList<Location>();
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
                locations.add(new Point(currentElement[1], currentElement[2]));
            }

            if (nextElement[0] == PathIterator.SEG_LINETO) {

                locations.add(new Point(nextElement[1], nextElement[2]));

            } else if (nextElement[0] == PathIterator.SEG_CLOSE) {
                locations.add(new Point(start[1], start[2]));
            }

        }

        return locations;
    }

    public void addPoints(int amount) {
        if (vertices.size() == 1) {
            for (int i = 0; i < amount; i++) {
                vertices.add(0, Location.midPoint(vertices.get(0), vertices.get(0)));
            }
        } else {
            for (int i = 0; i < amount; i++) {
                int randomIndex = (int) ((vertices.size() - 1) * Math.random());
                vertices.add(randomIndex + 1, Location.midPoint(vertices.get(randomIndex), vertices.get(randomIndex + 1)));
            }
        }
    }
}
