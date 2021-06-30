package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon extends Gobject {

    private int[] xs;
    private int[] ys;

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

    public Polygon(Color color, double ... path) {
        colorHolder = new ColorHolder(color);
        locations = new ArrayList<>();
        for(int i=0;i<path.length;i+=2){
            locations.add(new Point(path[i], path[i+1]));
        }
        this.xs = new int[locations.size()];
        this.ys = new int[locations.size()];
    }

    private void updateShape() {
        for (int i = 0; i < xs.length; i++) {
            xs[i] = (int) locations.get(i).getX();
            ys[i] = (int) locations.get(i).getY();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(colorHolder.getColor());
        updateShape();
        g.fillPolygon(xs,ys,xs.length);
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
}
