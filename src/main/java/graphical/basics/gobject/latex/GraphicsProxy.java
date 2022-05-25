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

    private final Graphics2D source;

    public GraphicsProxy(Graphics2D aux) {
        this.source = aux;
    }

    @Override
    public void draw(Shape shape) {
        source.draw(shape);
    }

    @Override
    public boolean drawImage(Image image, AffineTransform affineTransform, ImageObserver imageObserver) {
        return source.drawImage(image, affineTransform, imageObserver);
    }

    @Override
    public void drawImage(BufferedImage bufferedImage, BufferedImageOp bufferedImageOp, int i, int i1) {
        source.drawImage(bufferedImage, bufferedImageOp, i, i1);
    }

    @Override
    public void drawRenderedImage(RenderedImage renderedImage, AffineTransform affineTransform) {
        source.drawRenderedImage(renderedImage, affineTransform);
    }

    @Override
    public void drawRenderableImage(RenderableImage renderableImage, AffineTransform affineTransform) {
        source.drawRenderableImage(renderableImage, affineTransform);
    }

    @Override
    public Graphics create() {
        return source.create();
    }

    @Override
    public void translate(int i, int i1) {
        source.translate(i, i1);
    }

    @Override
    public void translate(double v, double v1) {
        source.translate(v, v1);
    }

    @Override
    public void rotate(double v) {
        source.rotate(v);
    }

    @Override
    public void rotate(double v, double v1, double v2) {
        source.rotate(v, v1, v2);
    }

    @Override
    public void scale(double v, double v1) {
        source.scale(v, v1);
    }

    @Override
    public void shear(double v, double v1) {
        source.shear(v, v1);
    }

    @Override
    public void transform(AffineTransform affineTransform) {
        source.transform(affineTransform);
    }

    @Override
    public void setTransform(AffineTransform affineTransform) {
        source.setTransform(affineTransform);
    }

    @Override
    public AffineTransform getTransform() {
        return source.getTransform();
    }

    @Override
    public Paint getPaint() {
        return source.getPaint();
    }

    @Override
    public Composite getComposite() {
        return source.getComposite();
    }

    @Override
    public void setBackground(Color color) {
        source.setBackground(color);
    }

    @Override
    public Color getBackground() {
        return source.getBackground();
    }

    @Override
    public Stroke getStroke() {
        return source.getStroke();
    }

    @Override
    public void clip(Shape shape) {
        source.clip(shape);
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return source.getFontRenderContext();
    }

    @Override
    public Color getColor() {
        return source.getColor();
    }

    @Override
    public void setColor(Color color) {
        source.setColor(color);
    }

    @Override
    public void setPaintMode() {
        source.setPaintMode();
    }

    @Override
    public void setXORMode(Color color) {
        source.setXORMode(color);
    }

    @Override
    public Font getFont() {
        return source.getFont();
    }

    @Override
    public void setFont(Font font) {
        source.setFont(font);
    }

    @Override
    public FontMetrics getFontMetrics(Font font) {
        return source.getFontMetrics(font);
    }

    @Override
    public Rectangle getClipBounds() {
        return source.getClipBounds();
    }

    @Override
    public void clipRect(int i, int i1, int i2, int i3) {
        source.clipRect(i, i1, i2, i3);
    }

    @Override
    public void setClip(int i, int i1, int i2, int i3) {
        source.clipRect(i, i1, i2, i3);
    }

    @Override
    public Shape getClip() {
        return source.getClip();
    }

    @Override
    public void setClip(Shape shape) {
        source.setClip(shape);
    }

    @Override
    public void copyArea(int i, int i1, int i2, int i3, int i4, int i5) {
        source.copyArea(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawLine(int i, int i1, int i2, int i3) {
        source.drawLine(1, i1, i2, i3);
    }

    @Override
    public void fillRect(int i, int i1, int i2, int i3) {
        source.fillRect(i, i1, i2, i3);
    }

    @Override
    public void clearRect(int i, int i1, int i2, int i3) {
        source.clipRect(i, i1, i2, i3);
    }

    @Override
    public void drawRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        source.drawRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void fillRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        source.fillRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawOval(int i, int i1, int i2, int i3) {
        source.drawOval(i, i1, i2, i3);
    }

    @Override
    public void fillOval(int i, int i1, int i2, int i3) {
        source.fillOval(i, i1, i2, i3);
    }

    @Override
    public void drawArc(int i, int i1, int i2, int i3, int i4, int i5) {
        source.drawArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void fillArc(int i, int i1, int i2, int i3, int i4, int i5) {
        source.fillArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawPolyline(int[] ints, int[] ints1, int i) {
        source.drawPolyline(ints, ints1, i);
    }

    @Override
    public void drawPolygon(int[] ints, int[] ints1, int i) {
        source.drawPolygon(ints, ints1, i);
    }

    @Override
    public void fillPolygon(int[] ints, int[] ints1, int i) {
        source.fillPolygon(ints, ints1, i);
    }

    @Override
    public void drawString(String s, int i, int i1) {
        source.drawString(s, i, i1);
    }

    @Override
    public void drawString(String s, float v, float v1) {
        source.drawString(s, v, v1);
    }

    @Override
    public void drawString(AttributedCharacterIterator attributedCharacterIterator, int i, int i1) {
        source.drawString(attributedCharacterIterator, i, i1);
    }

    @Override
    public void drawString(AttributedCharacterIterator attributedCharacterIterator, float v, float v1) {
        source.drawString(attributedCharacterIterator, v, v1);
    }

    @Override
    public void drawGlyphVector(GlyphVector glyphVector, float v, float v1) {
        source.drawGlyphVector(glyphVector, v, v1);
    }

    @Override
    public void fill(Shape shape) {
        source.fill(shape);
    }

    @Override
    public boolean hit(Rectangle rectangle, Shape shape, boolean b) {
        return source.hit(rectangle, shape, b);
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return source.getDeviceConfiguration();
    }

    @Override
    public void setComposite(Composite composite) {
        source.setComposite(composite);
    }

    @Override
    public void setPaint(Paint paint) {
        source.setPaint(paint);
    }

    @Override
    public void setStroke(Stroke stroke) {
        source.setStroke(stroke);
    }

    @Override
    public void setRenderingHint(RenderingHints.Key key, Object o) {
        source.setRenderingHint(key, o);
    }

    @Override
    public Object getRenderingHint(RenderingHints.Key key) {
        return source.getRenderingHint(key);
    }

    @Override
    public void setRenderingHints(Map<?, ?> map) {
        source.setRenderingHints(map);
    }

    @Override
    public void addRenderingHints(Map<?, ?> map) {
        source.addRenderingHints(map);
    }

    @Override
    public RenderingHints getRenderingHints() {
        return source.getRenderingHints();
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, ImageObserver imageObserver) {
        return source.drawImage(image, 1, i1, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, ImageObserver imageObserver) {
        return source.drawImage(image, i, i1, i2, i3, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, Color color, ImageObserver imageObserver) {
        return source.drawImage(image, i, i1, color, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, Color color, ImageObserver imageObserver) {
        return source.drawImage(image, i, i1, i2, i3, color, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, ImageObserver imageObserver) {
        return source.drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, Color color, ImageObserver imageObserver) {
        return source.drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, color, imageObserver);
    }

    @Override
    public void dispose() {
        source.dispose();
    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y) {
        source.drawChars(data, offset, length, x, y);
    }
}
