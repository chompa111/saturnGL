package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.presentation.RTAnimation;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JComponentGobject extends Gobject {

    Location refference;
    SaturnJComponent saturnJComponent;

    @Override
    public void paint(Graphics g) {
        g.translate((int) refference.getX(), (int) refference.getY());
        saturnJComponent.getContainer().paint(g);
        //saturnJComponent.update(refference);
        g.translate(-(int) refference.getX(), -(int) refference.getY());
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(refference, refference);
    }

    @Override
    public List<ColorHolder> getColors() {
        return List.of();
    }

    @Override
    public List<Location> getReferenceLocations() {
        return List.of(refference);
    }

    public JComponentGobject(Location refference, JComponent jComponent) {
        this.refference = refference;
        this.saturnJComponent = new SaturnJComponent(jComponent);
        this.saturnJComponent.dadRef = this;
    }
}
