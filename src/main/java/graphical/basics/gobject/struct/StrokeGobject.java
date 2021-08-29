package graphical.basics.gobject.struct;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.DynamicPath;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;
import org.apache.batik.ext.awt.geom.PathLength;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class StrokeGobject extends ShapeGobject2 {

    NumberHolder perc = new DoubleHolder(0);
    double len;


    public StrokeGobject(Shape shape, Color fillColor, Color strokeColor) {
        super(shape, fillColor, strokeColor);
    }

    public StrokeGobject(ShapeGobject2 shapeGobject2) {
        this.strokeColorHolder = shapeGobject2.strokeColorHolder != null ? new ColorHolder(shapeGobject2.strokeColorHolder.getColor())
                : shapeGobject2.fillColorHolder != null ? new ColorHolder(shapeGobject2.fillColorHolder.getColor()) : new ColorHolder(new Color(0, 0, 0, 0));
        this.shapeOfsetX = shapeGobject2.shapeOfsetX;
        this.shapeOfsetY = shapeGobject2.shapeOfsetY;
        this.shape = shapeGobject2.shape;
        this.location = shapeGobject2.location;
        this.angle = shapeGobject2.getAngle();
        this.scale = shapeGobject2.getScale();
        this.strokeThickness=shapeGobject2.getStrokeThickness();

        // funciona bem pra figuras

        len= DynamicPath.subshapes(shape).stream().map(si-> new PathLength(si).lengthOfPath()).mapToDouble(x->(double) x).max().orElse(0);

       // len = new PathLength(shape).lengthOfPath();


    }

    public StrokeGobject(ShapeGobject2 shapeGobject2, Color color) {
        this.strokeColorHolder = new ColorHolder(color);
        this.shapeOfsetX = shapeGobject2.shapeOfsetX;
        this.shapeOfsetY = shapeGobject2.shapeOfsetY;
        this.shape = shapeGobject2.shape;
        this.location = shapeGobject2.location;
        this.strokeThickness=shapeGobject2.getStrokeThickness();

        len = new PathLength(shape).lengthOfPath();
    }


    @Override
    public void paint(Graphics g) {
        var g2d = ((Graphics2D) g);

        if (len == 0) return;
        if(perc.getValue()==0)return;
        if (strokeColorHolder != null)
            g.setColor(strokeColorHolder.getColor());
        Stroke s = new BasicStroke((float) this.strokeThickness.getValue(),                      // Width
                BasicStroke.CAP_SQUARE,    // End cap
                BasicStroke.JOIN_MITER,    // Join style
                10.0f,                     // Miter limit
                new float[]{(float) (perc.getValue() * len), (float) len}, // Dash pattern
                0.0f);
        g2d.setStroke(s);

        var transf = g2d.getTransform();
        g2d.translate(location.getX() - shapeOfsetX, location.getY() - shapeOfsetY);

        if (strokeColorHolder != null) {
            g.setColor(strokeColorHolder.getColor());
            g2d.draw(shape);
        }

        g2d.setTransform(transf);
    }

    public NumberHolder getPerc() {
        return perc;
    }

    public void setPerc(NumberHolder perc) {
        this.perc = perc;
    }
}
