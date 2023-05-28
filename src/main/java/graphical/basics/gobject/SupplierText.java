package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

import static graphical.basics.gobject.latex.Latex.generateExp;

public class SupplierText extends Group {

    Font f;
    Location location;
    ColorHolder color;

    public SupplierText(Supplier<String> supplier, Location location, Color color) {
        isLatex = true;
        this.location = location;
        this.color = new ColorHolder(color);
        this.supplier = supplier;

        if (isLatex) {
            this.setGobjects(generateExp(supplier.get(), location, this.color.getColor()));
        } else {
            this.setGobjects(TextGobject.generateText(f, supplier.get(), location, this.color.getColor()));
        }
    }

    public SupplierText(Font font, Supplier<String> supplier, Location location, Color color) {
        isLatex = false;
        this.location = location;
        this.color = new ColorHolder(color);
        this.supplier = supplier;
    }

    Supplier<String> supplier;
    boolean isLatex;

    @Override
    public void paint(Graphics g) {
        List<Gobject> gobjects;
        if (isLatex) {
            gobjects = generateExp(supplier.get(), location, color.getColor());
        } else {
            gobjects = TextGobject.generateText(f, supplier.get(), location, color.getColor());
        }
        this.setGobjects(gobjects);
        super.paint(g);
    }

    @Override
    public List<ColorHolder> getColors() {
        return List.of(color);
    }

    @Override
    public List<Location> getReferenceLocations() {
        return List.of(location);
    }
}
