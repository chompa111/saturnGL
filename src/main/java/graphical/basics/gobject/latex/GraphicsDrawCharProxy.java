package graphical.basics.gobject.latex;


import graphical.basics.gobject.struct.Char2;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class GraphicsDrawCharProxy extends GraphicsProxy {

    private final List<Gobject> charList;
    private final Color color;

    public GraphicsDrawCharProxy(final Graphics2D aux, final Color color) {
        super(aux);
        this.color = color;
        charList = new ArrayList<>();
    }

    @Override
    public void drawChars(final char[] data, int offset, int length, int x, int y) {
        charList.add(buildChar(data));
        super.drawChars(data, offset, length, x, y);
    }

    private Char2 buildChar(final char[] data) {
        var font = getFont();
        var transform = getTransform();
        var xx = transform.getTranslateX();
        var yy = transform.getTranslateY();
        var size = (int) (transform.getScaleX() * 100);
        final var dynamicLocation = new Point((int) xx, (int) yy);
        return new Char2(font, dynamicLocation, data, size, color);
    }

    @Override
    public void fill(final Shape shape) {
        if (shape instanceof Rectangle2D) {
            var transform = getTransform();
            var size = transform.getScaleX();
            var rect = (Rectangle2D) shape;
            final var widthOffSet = rect.getWidth() * size;
            final var heightOffSet = rect.getHeight() * size;
            final var upperLeftDiagonal = new Point(rect.getX() * size, rect.getY() * size);
            final var lowerRightDiagonal = new Point(
                    upperLeftDiagonal.getX() + widthOffSet,
                    upperLeftDiagonal.getY() + heightOffSet);
            charList.add(new Rect(upperLeftDiagonal, lowerRightDiagonal, Color.white));
        }
        super.fill(shape);
    }

    public List<Gobject> getCharList() {
        return charList;
    }
}
