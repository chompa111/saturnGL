package graphical.basics.gobject.shape;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.FillAndStroke;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.ShapeGobject;

import java.awt.*;

public interface ShapeLike {
    Shape asShape();

    default ShapeGobject asShapeGobject() {
        var shape = this.asShape();
        if (this instanceof FillAndStroke) {
            var fs = (FillAndStroke) this;
            var shapegobject = new ShapeGobject(shape, fs.getFillColorHolder() != null ? new ColorHolder(fs.getFillColorHolder().getColor()) : null, fs.getStrokeColorHolder() != null ? new ColorHolder(fs.getStrokeColorHolder().getColor()) : null);
            shapegobject.setStrokeThickness(fs.getStrokeThickness());
            return shapegobject;
        } else if (this instanceof Gobject) {
            var g = (Gobject) this;
            //caso default
            return new ShapeGobject(shape, new ColorHolder(g.getColors().get(0).getColor()), null);
        }
        return null;
    }
}
