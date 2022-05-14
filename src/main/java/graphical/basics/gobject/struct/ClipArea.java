package graphical.basics.gobject.struct;

import graphical.basics.gobject.Group;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClipArea extends Group {

    Gobject gobject;
    ShapeLike shapeLike;


    public ClipArea(ShapeLike shapeLike) {
        this.shapeLike = shapeLike;

        if (!(shapeLike instanceof Gobject)) {
            throw new RuntimeException("shape-like needs to be a gobject instance!");
        }

        gobject = (Gobject) shapeLike;
    }

    @Override
    public void paint(Graphics g) {
        // cast em operações de grafico talvez sejam motivos de bugs vizuais quando graficos estiverem com escala
        // alterada (zoom)

        var oldClipArea = g.getClipBounds();

//        g.setColor(Color.magenta);
//        g.drawRect((int) p1.getX(), (int) p1.getY(), (int) locationPair.getwidth(), (int) locationPair.getheight());
        g.setClip(shapeLike.asShape());

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
        return gobject.getBorders();
    }


    @Override
    public List<Location> getRefereceLocations() {
        var list = new ArrayList<Location>();
        for (Gobject gobject : getGobjects()) {
            list.addAll(gobject.getRefereceLocations());
        }
        return list;
    }


}
