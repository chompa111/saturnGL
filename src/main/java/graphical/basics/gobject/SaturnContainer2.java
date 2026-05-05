package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.presentation.RTAnimation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class SaturnContainer2 extends Gobject {
    Component jComponent;
    JPanel container;
    BufferedImage bufferedImage;

    Location reflocation = Location.at(0, 0);


    public SaturnContainer2(Component jComponent) {
        this.jComponent = jComponent;
        container = new JPanel() {

            @Override
            public void paint(Graphics g) {
                super.paint(g);
            }

            @Override
            protected void paintChildren(Graphics g) {
                super.paintChildren(g);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }

            @Override
            public Graphics getGraphics() {
                return bufferedImage.getGraphics();
            }

            @Override
            protected Graphics getComponentGraphics(Graphics g) {
                return bufferedImage.getGraphics();
            }


        };
        container.add(jComponent);
        var dim = jComponent.getPreferredSize();
        container.setOpaque(true);
        container.setVisible(true);
        container.setSize(dim);
        bufferedImage = new BufferedImage(dim.width, dim.height, TYPE_INT_ARGB);

        RTAnimation.staticReference.getAnimationFrame().getFrame().add(container);
        RTAnimation.staticReference.getAnimationFrame().getFrame().setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.translate((int) reflocation.getX(), (int) reflocation.getY());
        g.drawImage(bufferedImage, 0, 0, null);
        g.translate(-(int) reflocation.getX(), -(int) reflocation.getY());
    }

    @Override
    public LocationPair getBorders() {
        var dim = jComponent.getPreferredSize();
        return new LocationPair(reflocation.copy(), reflocation.plus(dim.width, dim.height));
    }

    @Override
    public java.util.List<ColorHolder> getColors() {
        return java.util.List.of();
    }

    @Override
    public java.util.List<Location> getReferenceLocations() {
        return List.of(reflocation);
    }
}
