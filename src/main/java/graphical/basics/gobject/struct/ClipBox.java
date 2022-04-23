package graphical.basics.gobject.struct;

import graphical.basics.gobject.Group;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClipBox extends Group {


    private Location p1;
    private Location p2;

    private LocationPair locationPair;

    public ClipBox(Location p1, Location p2) {
        this.p1 = p1;
        this.p2 = p2;
        locationPair = new LocationPair(p1, p2);
    }

    @Override
    public void paint(Graphics g) {
        // cast em operações de grafico talvez sejam motivos de bugs vizuais quando graficos estiverem com escala
        // alterada (zoom)

        var oldClipArea = g.getClipBounds();

//        g.setColor(Color.magenta);
//        g.drawRect((int) p1.getX(), (int) p1.getY(), (int) locationPair.getwidth(), (int) locationPair.getheight());
        g.setClip((int) p1.getX(), (int) p1.getY(), (int) locationPair.getwidth(), (int) locationPair.getheight());


        for (Gobject gobject : getGobjects()) {
            gobject.paint(g, true);
        }
        if (oldClipArea != null) {
            g.setClip(oldClipArea.x, oldClipArea.y, oldClipArea.width, oldClipArea.height);
        } else {
            g.setClip(null);
        }


    }

    @Override
    public LocationPair getBorders() {
        return locationPair;
    }


    @Override
    public List<Location> getRefereceLocations() {
        var list = new ArrayList<Location>();
        for (Gobject gobject : getGobjects()) {
            list.addAll(gobject.getRefereceLocations());
        }
        list.add(p1);
        list.add(p2);

        return list;
    }


}
