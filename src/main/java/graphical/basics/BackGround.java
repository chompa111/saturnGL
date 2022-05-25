package graphical.basics;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class BackGround extends Gobject {

    ColorHolder backGroundColor = new ColorHolder(Color.black);

    int width;
    int height;

    public BackGround(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(backGroundColor.getColor());
        g.fillRect(0, 0, width, height);
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(backGroundColor);
    }

    @Override
    public List<Location> getReferenceLocations() {
        return null;
    }

    public ColorHolder getBackGroundColor() {
        return backGroundColor;
    }
}
