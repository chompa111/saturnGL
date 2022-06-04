package graphical.basics.gobject.struct;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;

import java.awt.geom.*;

public class ShapeCopyMachine {
    public static Shape clone(Shape shape) {
        if (shape == null) {
            return null;
        }
        Shape clone;
        if (shape instanceof Line2D) {
            clone = (shape instanceof Line2D.Float) ? new Line2D.Float()
                    : new Line2D.Double();
            ((Line2D) clone).setLine((Line2D) shape);
        } else if (shape instanceof Rectangle) {
            clone = new Rectangle((Rectangle) shape);
        } else if (shape instanceof Rectangle2D) {
            clone = (shape instanceof Rectangle2D.Float) ? new Rectangle2D.Float()
                    : new Rectangle2D.Double();
            ((Rectangle2D) clone).setRect((Rectangle2D) shape);
        } else if (shape instanceof RoundRectangle2D) {
            clone = (shape instanceof RoundRectangle2D.Float) ? new RoundRectangle2D.Float()
                    : new RoundRectangle2D.Double();
            ((RoundRectangle2D) clone)
                    .setRoundRect((RoundRectangle2D) shape);
        } else if (shape instanceof Ellipse2D) {
            clone = (shape instanceof Ellipse2D.Float) ? new Ellipse2D.Float()
                    : new Ellipse2D.Double();
            ((Ellipse2D) clone).setFrame(((Ellipse2D) shape).getFrame());
        } else if (shape instanceof Arc2D) {
            clone = (shape instanceof Arc2D.Float) ? new Arc2D.Float()
                    : new Arc2D.Double();
            ((Arc2D) clone).setArc((Arc2D) shape);
        } else if (shape instanceof Polygon) {
            Polygon p = (Polygon) shape;
            clone = new Polygon(p.xpoints, p.ypoints, p.npoints);
        } else if (shape instanceof CubicCurve2D) {
            clone = (shape instanceof CubicCurve2D.Float) ? new CubicCurve2D.Float()
                    : new CubicCurve2D.Double();
            ((CubicCurve2D) clone).setCurve((CubicCurve2D) shape);
        } else if (shape instanceof QuadCurve2D) {
            clone = (shape instanceof QuadCurve2D.Float) ? new QuadCurve2D.Float()
                    : new QuadCurve2D.Double();
            ((QuadCurve2D) clone).setCurve((QuadCurve2D) shape);
        } else if (shape instanceof Path2D.Float) {
            clone = new Path2D.Float(shape);
        } else {
            clone = new Path2D.Double(shape);
        }
        return clone;
    }
}