package graphical.basics.gobject.shape;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.FillAndStroke;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.gobject.struct.StrokeGobject;

import java.awt.*;

public interface ShapeLike {
    Shape asShape();

    default ShapeGobject2 asShapeGobject() {
        var shape = this.asShape();
        if (this instanceof FillAndStroke) {
            var fs = (FillAndStroke) this;
            return new ShapeGobject2(shape, fs.getFillColorHolder() != null ? new ColorHolder(fs.getFillColorHolder().getColor()) : null, fs.getStrokeColorHolder() != null ? new ColorHolder(fs.getStrokeColorHolder().getColor()) : null);
        } else if (this instanceof Gobject) {
            var g = (Gobject) this;

            return new ShapeGobject2(shape, new ColorHolder(g.getColors().get(0).getColor()), null);
        }
        return null;
    }
}
