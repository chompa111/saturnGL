package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class Img extends Gobject {

    Location location;

    BufferedImage bufferedImage;

    public Img(Location location, String path) {
        try {
            bufferedImage = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.location = location;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, (int) location.getX(), (int) location.getY(), null);
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
