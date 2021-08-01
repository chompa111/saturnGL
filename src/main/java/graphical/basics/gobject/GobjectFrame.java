package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.task.transformation.gobject.Pixel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GobjectFrame extends Gobject {

    Gobject gobject;

    BufferedImage bufferedImage;


    List<Pixel> pixels;

    public GobjectFrame(Gobject gobject) {
        bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        this.gobject = gobject;

        pixels = new ArrayList<>();
        gobject.paint(bufferedImage.getGraphics());
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                var value = bufferedImage.getRGB(i, j);
                if (value != 0) {
                    pixels.add(new Pixel(new Color(value,true), i, j));
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        for(int i=0;i<pixels.size();i++){
            pixels.get(i).paint(g);
        }
    }

    @Override
    public LocationPair getBorders() {
        return gobject.getBorders();
    }

    @Override
    public List<ColorHolder> getColors() {
        return gobject.getColors();
    }

    @Override
    public List<Location> getRefereceLocations() {
        return gobject.getRefereceLocations();
    }

    public Gobject getGobject() {
        return gobject;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public List<Pixel> getPixels() {
        return pixels;
    }
}
