package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.presentation.RTAnimation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SaturnContainer extends Gobject {

    Component jComponent;
    JPanel container;

    Location reflocation = Location.at(0, 0);


    public SaturnContainer(Component jComponent) {
        this.jComponent = jComponent;
        container = new JPanel();
        container.add(jComponent);
        var dim = jComponent.getPreferredSize();
        container.setOpaque(true);
        container.setVisible(true);
        container.setSize(dim);
        RTAnimation.staticReference.getAnimationFrame().getFrame().add(container);
        RTAnimation.staticReference.getAnimationFrame().getFrame().setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        if (true) {
            updateFrameBounds();
            container.revalidate();
        }

        ((Graphics2D) g).translate(reflocation.getX() , reflocation.getY() );
        container.paint(g);
        ((Graphics2D) g).translate(-(reflocation.getX()), -(reflocation.getY()));
    }

    private void updateFrameBounds() {
        var dim = jComponent.getPreferredSize();
        container.setBounds((int) reflocation.getX(), (int) reflocation.getY(), dim.width, dim.height);
    }

    @Override
    public LocationPair getBorders() {
        var dim = jComponent.getPreferredSize();

        return new LocationPair(Location.at(reflocation.getX(), reflocation.getY()), Location.at(reflocation.getX(), reflocation.getY()).plus(dim.width, dim.height));
    }

    @Override
    public List<ColorHolder> getColors() {
        return List.of();
    }

    @Override
    public List<Location> getReferenceLocations() {
        return List.of(reflocation);
    }
}
