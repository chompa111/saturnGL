package graphical.basics.gobject.struct;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.DynamicPath;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;
import org.apache.batik.ext.awt.geom.PathLength;
import java.awt.Stroke;
import java.awt.*;

public class StrokeGobject extends ShapeGobject {

    NumberHolder perc = new DoubleHolder(0);
    double len;


    public StrokeGobject(Shape shape, Color fillColor, Color strokeColor) {
        super(shape, fillColor, strokeColor);
    }

    public StrokeGobject(ShapeGobject shapeGobject) {
        this.strokeColorHolder = shapeGobject.strokeColorHolder != null ? new ColorHolder(shapeGobject.strokeColorHolder.getColor())
                : shapeGobject.fillColorHolder != null ? new ColorHolder(shapeGobject.fillColorHolder.getColor()) : new ColorHolder(new Color(0, 0, 0, 0));
        this.shapeOfsetX = shapeGobject.shapeOfsetX;
        this.shapeOfsetY = shapeGobject.shapeOfsetY;
        this.shape = shapeGobject.shape;
        this.location = shapeGobject.location;
        this.angle = shapeGobject.getAngle();
        this.scale = shapeGobject.getScale();
        this.setStrokeThickness(shapeGobject.getStrokeThickness());
        this.setStroke(shapeGobject.getStroke().copy());

        // funciona bem pra figuras

        len= DynamicPath.subshapes(shape).stream().map(si-> new PathLength(si).lengthOfPath()).mapToDouble(x->(double) x).max().orElse(0);

       // len = new PathLength(shape).lengthOfPath();


    }

    public StrokeGobject(ShapeGobject shapeGobject, Color color) {
        this.strokeColorHolder = new ColorHolder(color);
        this.shapeOfsetX = shapeGobject.shapeOfsetX;
        this.shapeOfsetY = shapeGobject.shapeOfsetY;
        this.shape = shapeGobject.shape;
        this.location = shapeGobject.location;
        this.setStrokeThickness(shapeGobject.getStrokeThickness());

        len = new PathLength(shape).lengthOfPath();
    }


    @Override
    public void paint(Graphics g) {
        var g2d = ((Graphics2D) g);

        if (len == 0) return;
        if(perc.getValue()==0)return;
        if (strokeColorHolder != null)
            g.setColor(strokeColorHolder.getColor());


        Stroke s = new BasicStroke((float) getStroke().getStrokeThickness().getValue(),                      // Width
                BasicStroke.CAP_SQUARE,    // End cap
                BasicStroke.JOIN_MITER,    // Join style
                10.0f,                     // Miter limit
                new float[]{(float) (Math.abs(perc.getValue()) * len), (float) len}, // Dash pattern
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
