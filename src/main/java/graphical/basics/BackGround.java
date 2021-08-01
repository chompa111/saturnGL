package graphical.basics;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class BackGround extends Gobject {

    ColorHolder backGroundColor=new ColorHolder(Color.black);

    @Override
    public void paint(Graphics g) {
        g.setColor(backGroundColor.getColor());
        g.fillRect(0,0,1000,1000);
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
    public List<Location> getRefereceLocations() {
        return null;
    }
}
