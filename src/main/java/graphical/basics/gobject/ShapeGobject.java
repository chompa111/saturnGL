package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.util.List;

public class ShapeGobject extends Gobject {
    ColorHolder colorHolder;
    Shape shape;




    public ShapeGobject(Shape shape, String style) {
        for (String stylePart : style.split(";")) {
            var value = stylePart.split(":")[1];
            switch (stylePart.split(":")[0]) {
                case "fill":

                    if (!value.equals("none"))
                        colorHolder = new ColorHolder(ColorHolder.hex2Rgb(stylePart.split(":")[1]));
                    break;

            }
        }

        this.shape = shape;
    }

    @Override
    public void paint(Graphics g) {

        if (colorHolder != null) {
            g.setColor(colorHolder.getColor());
            ((Graphics2D) g).fill(shape);
        }

        ((Graphics2D) g).draw(shape);

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
