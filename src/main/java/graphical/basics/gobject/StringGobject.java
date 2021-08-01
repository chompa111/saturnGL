package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class StringGobject extends Gobject {

    Location location;
    ColorHolder colorHolder;
    Supplier<String> string;

    public StringGobject(Location location, Supplier<String> string, Color color) {
        this.location = location;
        this.colorHolder = new ColorHolder(color);
        this.string = string;
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,30));
        g.setColor(colorHolder.getColor());
        g.drawString(string.get(), (int) location.getX(), (int) location.getY());
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(location,location);
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList(colorHolder);
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(location);
    }
}
