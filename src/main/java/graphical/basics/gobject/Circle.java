package graphical.basics.gobject;


import graphical.basics.ColorHolder;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.List;

public class Circle extends Gobject implements ShapeLike {

    Location center;
    NumberHolder radius;
    ColorHolder colorHolder;

    public Circle(Location center, NumberHolder radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.colorHolder = new ColorHolder(color);
    }

    @Override
    public void paint(Graphics g) {
        int r = (int) radius.getValue();
        g.setColor(colorHolder.getColor());
        g.fillOval((int) center.getX() - r / 2, (int) center.getY() - r / 2, r, r);

//        try {
//            var border = getBorders();
//            var b = ImageIO.read(getClass().getResource("/3px-tile.png"));
//            ((Graphics2D) g).setPaint(new TexturePaint(b, new Rectangle((int)border.getL1().getX(), (int)border.getL1().getY(), 200, 200)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        g.fillOval((int) center.getX() - r / 2, (int) center.getY() - r / 2, r, r);


    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(new Point(center.getX() - radius.getValue() / 2, center.getY() - radius.getValue() / 2),
                new Point(center.getX() + radius.getValue() / 2, center.getY() + radius.getValue() / 2));
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(colorHolder);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(center);
    }

    public NumberHolder getRadius() {
        return radius;
    }

    public void setRadius(NumberHolder radius) {
        this.radius = radius;
    }

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public ColorHolder getColorHolder() {
        return colorHolder;
    }

    public void setColorHolder(ColorHolder colorHolder) {
        this.colorHolder = colorHolder;
    }


    @Override
    public Shape asShape() {
        return new Ellipse2D.Double(center.getX() - radius.getValue() / 2, center.getY() - radius.getValue() / 2, radius.getValue(), radius.getValue());
    }
}
