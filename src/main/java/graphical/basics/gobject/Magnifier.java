package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.presentation.Animation;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Magnifier extends Gobject {


    private Location p1;
    private Location p2;

    private LocationPair locationPair;

    public Magnifier(Location p1, Location p2) {
        this.p1 = p1;
        this.p2 = p2;

        locationPair = new LocationPair(p1, p2);
    }


    @Override
    public void paint(Graphics g,boolean x) {




        var oldClipArea = g.getClipBounds();
        g.setClip((int) p1.getX(), (int) p1.getY(), (int) locationPair.getwidth(), (int) locationPair.getheight());

        g.setColor(Color.BLACK);
        g.fillRect((int) p1.getX(), (int) p1.getY(), (int) locationPair.getwidth(), (int) locationPair.getheight());

        var midpoint = getBorders().midPoint();
        var g2d = (Graphics2D) g;
        var oldT = (AffineTransform) g2d.getTransform().clone();
        g2d.translate(midpoint.getX(), midpoint.getY());
        g2d.rotate(angle.getValue());
        g2d.scale(scale.getValue(), scale.getValue());
        g2d.translate(-midpoint.getX(), -midpoint.getY());
        paint(g);

        g2d.setTransform(oldT);

        g.setColor(new Color(0,200,200,20));
        g.fillRect((int) p1.getX(), (int) p1.getY(), (int) locationPair.getwidth(), (int) locationPair.getheight());






//          g.fillRect((int) p1.getX(), (int) p1.getY(), (int) (p2.getX() - p1.getX()), (int) (p2.getY() - p1.getY()));

        if (oldClipArea != null) {
            g.setClip(oldClipArea.x, oldClipArea.y, oldClipArea.width, oldClipArea.height);
        } else {
            g.setClip(null);
        }
    }

    @Override
    public void paint(Graphics g) {




        for (Gobject gobject : Animation.staticReference.getGobjects()) {

            if(gobject.equals(this)){
                //avoiding recursion
                continue;
            }
            gobject.paint(g);
        }


    }

    @Override
    public LocationPair getBorders() {
        return locationPair;
    }

    @Override
    public List<ColorHolder> getColors() {
        return List.of();
    }

    @Override
    public List<Location> getReferenceLocations() {
        return List.of(p1, p2);
    }
}
