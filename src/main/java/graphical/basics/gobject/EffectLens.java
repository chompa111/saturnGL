package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EffectLens extends Gobject {

    LocationPair lp = new LocationPair(new Point(0, 0), new Point(0, 0));



    boolean once = false;

    @Override
    public void paint(Graphics g) {
        float[] matrix = new float[400];
        for (int i = 0; i < 400; i++)
            matrix[i] = 1.0f/400.0f;


      //  Presentation.staticReference.bufferedGraphics = Presentation.staticReference.bufferedImage.getGraphics();
    }

    @Override
    public LocationPair getBorders() {
        return lp;
    }

    @Override
    public List<ColorHolder> getColors() {
        return new ArrayList<>();
    }

    @Override
    public List<Location> getReferenceLocations() {
        return null;
    }
}
