package graphical.basics.gobject.latex;


import graphical.basics.gobject.struct.Char2;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Point;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class GraphicsdrawCharProxy extends GraphicsProxy {

    List<Gobject> charList;
    Color color;

    public GraphicsdrawCharProxy(Graphics2D slave, Color color) {
        super(slave);
        this.color = color;
        charList = new ArrayList<>();
    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y) {
        var font = getFont();
        var transform = getTransform();
        var xx = transform.getTranslateX();
        var yy = transform.getTranslateY();
        var size = (int) (transform.getScaleX() * 100);
        charList.add(new Char2(font, new Point((int)xx, (int)yy), data, size, color));
        super.drawChars(data, offset, length, x, y);
    }

    @Override
    public void fill(Shape shape) {
        if (shape instanceof Rectangle2D) {
            var transform = getTransform();
            var xx = transform.getTranslateX();
            var yy = transform.getTranslateY();
            var size =  transform.getScaleX();
            var rect = (Rectangle2D) shape;
            charList.add(new Rect(new Point(rect.getX()*size, rect.getY()*size), new Point((rect.getX() + rect.getWidth())*size, size*(rect.getY() + rect.getHeight())),Color.black));
        }
        super.fill(shape);
    }

    public List<Gobject> getCharList() {
        return charList;
    }
}
