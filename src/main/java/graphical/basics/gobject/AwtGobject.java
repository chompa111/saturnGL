package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class AwtGobject extends Gobject {
    private final Consumer<Graphics> graphicsConsumer;

    public AwtGobject(Consumer<Graphics> graphicsConsumer) {
        this.graphicsConsumer = graphicsConsumer;
    }

    @Override
    public void paint(Graphics g) {
        graphicsConsumer.accept(g);
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(Location.at(0, 0), Location.at(0, 0));
    }

    @Override
    public List<ColorHolder> getColors() {
        return List.of(new ColorHolder(new Color(0, 0, 0, 0)));
    }

    @Override
    public List<Location> getReferenceLocations() {
        return List.of(Location.at(0, 0));
    }
}
