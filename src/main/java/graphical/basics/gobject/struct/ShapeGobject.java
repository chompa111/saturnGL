package graphical.basics.gobject.struct;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.location.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

public class ShapeGobject extends FillAndStroke {

    protected Location location;
    protected Shape shape;

    protected double shapeOfsetX;
    protected double shapeOfsetY;

    public ShapeGobject() {
    }

    public ShapeGobject(Shape shape, Color fillColor, Color strokeColor) {
        super(fillColor, strokeColor);
        this.shape = shape;
        var bounds = shape.getBounds();
        shapeOfsetX = (bounds.x + bounds.width) / 2.0;
        shapeOfsetY = (bounds.y + bounds.height) / 2.0;

        location = new Point(shapeOfsetX, shapeOfsetY);
    }

    public ShapeGobject(Shape shape, Location location, Color fillColor, Color strokeColor) {
        super(fillColor, strokeColor);
        this.shape = shape;
        this.location = location;
        var bounds = shape.getBounds();
        shapeOfsetX = (bounds.x + bounds.width) / 2.0;
        shapeOfsetY = (bounds.y + bounds.height) / 2.0;

    }


    public ShapeGobject(Shape shape, Location location, ColorHolder fillColorHolder, ColorHolder strokeColorHolder) {
        super(fillColorHolder, strokeColorHolder);
        this.shape = shape;
        this.location = location;
        var bounds = shape.getBounds();
        shapeOfsetX = (bounds.x + bounds.width) / 2.0;
        shapeOfsetY = (bounds.y + bounds.height) / 2.0;

    }

    public ShapeGobject(Shape shape, ColorHolder fillColorHolder, ColorHolder strokeColorHolder) {
        super(fillColorHolder, strokeColorHolder);
        this.shape = shape;
        var bounds = shape.getBounds();
        shapeOfsetX = (bounds.x + bounds.width) / 2.0;
        shapeOfsetY = (bounds.y + bounds.height) / 2.0;
        location = new Point(shapeOfsetX, shapeOfsetY);
    }


    @Override
    public void paint(Graphics g) {
        //   g.setClip(null);
        var g2d = ((Graphics2D) g);

        ((Graphics2D) g).setStroke(getStroke().getStroke());

        var transf = g2d.getTransform();
        g2d.translate(location.getX() - shapeOfsetX, location.getY() - shapeOfsetY);

        if (fillColorHolder != null) {
            g.setColor(fillColorHolder.getColor());
            g2d.fill(shape);
        }
        if (strokeColorHolder != null) {
            g.setColor(strokeColorHolder.getColor());
            g2d.draw(shape);
        }

        g2d.setTransform(transf);
    }

    @Override
    public LocationPair getBorders() {
        var bounds = shape.getBounds();
        double x = bounds.getBounds().getX() + location.getX() - shapeOfsetX;
        double y = bounds.getY() + location.getY() - shapeOfsetY;
        return new LocationPair(new graphical.basics.location.Point(x, y), new Point(x + bounds.getWidth(), y + bounds.getHeight()));
    }

    @Override
    public List<Location> getReferenceLocations() {
        return Arrays.asList(location);
    }

    public Shape getShape() {
        var af = getTranformation();
        af.translate(location.getX() - shapeOfsetX, location.getY() - shapeOfsetY);

        return af.createTransformedShape(shape);
    }

    public static ShapeGobject fromSVGStyle(Shape shape, String style) {
//        if(style.equals("")){
//            return new ShapeGobject2(shape,Color.white,Color.black);
//        }

        ColorHolder fillColorHolder = null;
        ColorHolder strokeColorHolder = null;
        for (String stylePart : style.split(";")) {

            var value = stylePart.split(":")[1];
            switch (stylePart.split(":")[0]) {
                case "fill":

                    if(value.contains("rgb(")){
                        var component =value.replaceAll("[^0-9,]","").split(",");
                        fillColorHolder=new ColorHolder(new Color(Integer.parseInt(component[0]),Integer.parseInt(component[1]),Integer.parseInt(component[2])));
                        break;
                    }
                    if(value.equals("white")){
                        fillColorHolder = new ColorHolder(Color.white);
                        break;
                    }
                    if (!value.equals("none")) {
                        if (!value.contains("url"))
                            fillColorHolder = new ColorHolder(ColorHolder.hex2Rgb(stylePart.split(":")[1]));
                    }

                    break;
                case "stroke":
                    if (!value.equals("none")) {
                        if (!value.contains("url"))

                            strokeColorHolder = new ColorHolder(ColorHolder.hex2Rgb(stylePart.split(":")[1]));

                    }

                    break;

            }

        }
        return new ShapeGobject(shape, fillColorHolder, strokeColorHolder);
    }

    @Override
    public Gobject copy() {
        var copy = new ShapeGobject(ShapeCopyMachine.clone(shape),location.copy(), new ColorHolder(getFillColorHolder().getColor()), null);
        copy.shapeOfsetX=this.shapeOfsetX;
        copy.shapeOfsetY=this.shapeOfsetY;
        copyBasicFields(copy, this);
        return copy;
    }

    public void resize(AffineTransform af){
        shape = af.createTransformedShape(shape);
    }
}
