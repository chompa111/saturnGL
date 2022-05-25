package graphical.basics.gobject.latex;



import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class GraphicsProxy extends Graphics2D {

    private final Graphics2D aux;

    public GraphicsProxy(Graphics2D aux) {
        this.aux = aux;
    }

    @Override
    public void draw(Shape shape) {
        aux.draw(shape);
    }

    @Override
    public boolean drawImage(Image image, AffineTransform affineTransform, ImageObserver imageObserver) {
        return aux.drawImage(image, affineTransform, imageObserver);
    }

    @Override
    public void drawImage(BufferedImage bufferedImage, BufferedImageOp bufferedImageOp, int i, int i1) {
        aux.drawImage(bufferedImage, bufferedImageOp, i, i1);
    }

    @Override
    public void drawRenderedImage(RenderedImage renderedImage, AffineTransform affineTransform) {
        aux.drawRenderedImage(renderedImage, affineTransform);
    }

    @Override
    public void drawRenderableImage(RenderableImage renderableImage, AffineTransform affineTransform) {
        aux.drawRenderableImage(renderableImage, affineTransform);
    }

    @Override
    public Graphics create() {
        return aux.create();
    }

    @Override
    public void translate(int i, int i1) {
        aux.translate(i, i1);
    }

    @Override
    public void translate(double v, double v1) {
        aux.translate(v, v1);
    }

    @Override
    public void rotate(double v) {
        aux.rotate(v);
    }

    @Override
    public void rotate(double v, double v1, double v2) {
        aux.rotate(v, v1, v2);
    }

    @Override
    public void scale(double v, double v1) {
        aux.scale(v, v1);
    }

    @Override
    public void shear(double v, double v1) {
        aux.shear(v, v1);
    }

    @Override
    public void transform(AffineTransform affineTransform) {
        aux.transform(affineTransform);
    }

    @Override
    public void setTransform(AffineTransform affineTransform) {
        aux.setTransform(affineTransform);
    }

    @Override
    public AffineTransform getTransform() {
        return aux.getTransform();
    }

    @Override
    public Paint getPaint() {
        return aux.getPaint();
    }

    @Override
    public Composite getComposite() {
        return aux.getComposite();
    }

    @Override
    public void setBackground(Color color) {
        aux.setBackground(color);
    }

    @Override
    public Color getBackground() {
        return aux.getBackground();
    }

    @Override
    public Stroke getStroke() {
        return aux.getStroke();
    }

    @Override
    public void clip(Shape shape) {
        aux.clip(shape);
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return aux.getFontRenderContext();
    }

    @Override
    public Color getColor() {
        return aux.getColor();
    }

    @Override
    public void setColor(Color color) {
        aux.setColor(color);
    }

    @Override
    public void setPaintMode() {
        aux.setPaintMode();
    }

    @Override
    public void setXORMode(Color color) {
        aux.setXORMode(color);
    }

    @Override
    public Font getFont() {
        return aux.getFont();
    }

    @Override
    public void setFont(Font font) {
        aux.setFont(font);
    }

    @Override
    public FontMetrics getFontMetrics(Font font) {
        return aux.getFontMetrics(font);
    }

    @Override
    public Rectangle getClipBounds() {
        return aux.getClipBounds();
    }

    @Override
    public void clipRect(int i, int i1, int i2, int i3) {
        aux.clipRect(i, i1, i2, i3);
    }

    @Override
    public void setClip(int i, int i1, int i2, int i3) {
        aux.clipRect(i, i1, i2, i3);
    }

    @Override
    public Shape getClip() {
        return aux.getClip();
    }

    @Override
    public void setClip(Shape shape) {
        aux.setClip(shape);
    }

    @Override
    public void copyArea(int i, int i1, int i2, int i3, int i4, int i5) {
        aux.copyArea(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawLine(int i, int i1, int i2, int i3) {
        aux.drawLine(1, i1, i2, i3);
    }

    @Override
    public void fillRect(int i, int i1, int i2, int i3) {
        aux.fillRect(i, i1, i2, i3);
    }

    @Override
    public void clearRect(int i, int i1, int i2, int i3) {
        aux.clipRect(i, i1, i2, i3);
    }

    @Override
    public void drawRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        aux.drawRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void fillRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        aux.fillRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawOval(int i, int i1, int i2, int i3) {
        aux.drawOval(i, i1, i2, i3);
    }

    @Override
    public void fillOval(int i, int i1, int i2, int i3) {
        aux.fillOval(i, i1, i2, i3);
    }

    @Override
    public void drawArc(int i, int i1, int i2, int i3, int i4, int i5) {
        aux.drawArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void fillArc(int i, int i1, int i2, int i3, int i4, int i5) {
        aux.fillArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawPolyline(int[] ints, int[] ints1, int i) {
        aux.drawPolyline(ints, ints1, i);
    }

    @Override
    public void drawPolygon(int[] ints, int[] ints1, int i) {
        aux.drawPolygon(ints, ints1, i);
    }

    @Override
    public void fillPolygon(int[] ints, int[] ints1, int i) {
        aux.fillPolygon(ints, ints1, i);
    }

    @Override
    public void drawString(String s, int i, int i1) {
        aux.drawString(s, i, i1);
    }

    @Override
    public void drawString(String s, float v, float v1) {
        aux.drawString(s, v, v1);
    }

    @Override
    public void drawString(AttributedCharacterIterator attributedCharacterIterator, int i, int i1) {
        aux.drawString(attributedCharacterIterator, i, i1);
    }

    @Override
    public void drawString(AttributedCharacterIterator attributedCharacterIterator, float v, float v1) {
        aux.drawString(attributedCharacterIterator, v, v1);
    }

    @Override
    public void drawGlyphVector(GlyphVector glyphVector, float v, float v1) {
        aux.drawGlyphVector(glyphVector, v, v1);
    }

    @Override
    public void fill(Shape shape) {
        aux.fill(shape);
    }

    @Override
    public boolean hit(Rectangle rectangle, Shape shape, boolean b) {
        return aux.hit(rectangle, shape, b);
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return aux.getDeviceConfiguration();
    }

    @Override
    public void setComposite(Composite composite) {
        aux.setComposite(composite);
    }

    @Override
    public void setPaint(Paint paint) {
        aux.setPaint(paint);
    }

    @Override
    public void setStroke(Stroke stroke) {
        aux.setStroke(stroke);
    }

    @Override
    public void setRenderingHint(RenderingHints.Key key, Object o) {
        aux.setRenderingHint(key, o);
    }

    @Override
    public Object getRenderingHint(RenderingHints.Key key) {
        return aux.getRenderingHint(key);
    }

    @Override
    public void setRenderingHints(Map<?, ?> map) {
        aux.setRenderingHints(map);
    }

    @Override
    public void addRenderingHints(Map<?, ?> map) {
        aux.addRenderingHints(map);
    }

    @Override
    public RenderingHints getRenderingHints() {
        return aux.getRenderingHints();
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, ImageObserver imageObserver) {
        return aux.drawImage(image, 1, i1, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, ImageObserver imageObserver) {
        return aux.drawImage(image, i, i1, i2, i3, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, Color color, ImageObserver imageObserver) {
        return aux.drawImage(image, i, i1, color, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, Color color, ImageObserver imageObserver) {
        return aux.drawImage(image, i, i1, i2, i3, color, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, ImageObserver imageObserver) {
        return aux.drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, Color color, ImageObserver imageObserver) {
        return aux.drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, color, imageObserver);
    }

    @Override
    public void dispose() {
        aux.dispose();
    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y) {
        aux.drawChars(data, offset, length, x, y);
    }
}
