package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.latex.Char;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.List;

public class Text extends Gobject {

    List<Char> charList;

    public Text(String s, Location location) {
        for (int i = 0; i < s.length(); i++) {

        }
        this.charList = charList;
    }

    @Override
    public void paint(Graphics g) {

    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return null;
    }

    @Override
    public List<Location> getRefereceLocations() {
        return null;
    }
}
