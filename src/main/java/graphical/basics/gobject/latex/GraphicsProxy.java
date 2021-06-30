package graphical.basics.gobject.latex;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class GraphicsProxy extends Graphics2D {

    Graphics2D slave;

    public GraphicsProxy(Graphics2D slave) {
        this.slave = slave;
    }

    @Override
    public void draw(Shape shape) {
        slave.draw(shape);
    }

    @Override
    public boolean drawImage(Image image, AffineTransform affineTransform, ImageObserver imageObserver) {
        return slave.drawImage(image, affineTransform, imageObserver);
    }

    @Override
    public void drawImage(BufferedImage bufferedImage, BufferedImageOp bufferedImageOp, int i, int i1) {
        slave.drawImage(bufferedImage, bufferedImageOp, i, i1);
    }

    @Override
    public void drawRenderedImage(RenderedImage renderedImage, AffineTransform affineTransform) {
        slave.drawRenderedImage(renderedImage, affineTransform);
    }

    @Override
    public void drawRenderableImage(RenderableImage renderableImage, AffineTransform affineTransform) {
        slave.drawRenderableImage(renderableImage, affineTransform);
    }

    @Override
    public Graphics create() {
        return slave.create();
    }

    @Override
    public void translate(int i, int i1) {
        slave.translate(i, i1);
    }

    @Override
    public void translate(double v, double v1) {
        slave.translate(v, v1);
    }

    @Override
    public void rotate(double v) {
        slave.rotate(v);
    }

    @Override
    public void rotate(double v, double v1, double v2) {
        slave.rotate(v, v1, v2);
    }

    @Override
    public void scale(double v, double v1) {
        slave.scale(v, v1);
    }

    @Override
    public void shear(double v, double v1) {
        slave.shear(v, v1);
    }

    @Override
    public void transform(AffineTransform affineTransform) {
        slave.transform(affineTransform);
    }

    @Override
    public void setTransform(AffineTransform affineTransform) {
        slave.setTransform(affineTransform);
    }

    @Override
    public AffineTransform getTransform() {
        return slave.getTransform();
    }

    @Override
    public Paint getPaint() {
        return slave.getPaint();
    }

    @Override
    public Composite getComposite() {
        return slave.getComposite();
    }

    @Override
    public void setBackground(Color color) {
        slave.setBackground(color);
    }

    @Override
    public Color getBackground() {
        return slave.getBackground();
    }

    @Override
    public Stroke getStroke() {
        return slave.getStroke();
    }

    @Override
    public void clip(Shape shape) {
        slave.clip(shape);
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return slave.getFontRenderContext();
    }

    @Override
    public Color getColor() {
        return slave.getColor();
    }

    @Override
    public void setColor(Color color) {
        slave.setColor(color);
    }

    @Override
    public void setPaintMode() {
        slave.setPaintMode();
    }

    @Override
    public void setXORMode(Color color) {
        slave.setXORMode(color);
    }

    @Override
    public Font getFont() {
        return slave.getFont();
    }

    @Override
    public void setFont(Font font) {
        slave.setFont(font);
    }

    @Override
    public FontMetrics getFontMetrics(Font font) {
        return slave.getFontMetrics(font);
    }

    @Override
    public Rectangle getClipBounds() {
        return slave.getClipBounds();
    }

    @Override
    public void clipRect(int i, int i1, int i2, int i3) {
        slave.clipRect(i, i1, i2, i3);
    }

    @Override
    public void setClip(int i, int i1, int i2, int i3) {
        slave.clipRect(i, i1, i2, i3);
    }

    @Override
    public Shape getClip() {
        return slave.getClip();
    }

    @Override
    public void setClip(Shape shape) {
        slave.setClip(shape);
    }

    @Override
    public void copyArea(int i, int i1, int i2, int i3, int i4, int i5) {
        slave.copyArea(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawLine(int i, int i1, int i2, int i3) {
        slave.drawLine(1, i1, i2, i3);
    }

    @Override
    public void fillRect(int i, int i1, int i2, int i3) {
        slave.fillRect(i, i1, i2, i3);
    }

    @Override
    public void clearRect(int i, int i1, int i2, int i3) {
        slave.clipRect(i, i1, i2, i3);
    }

    @Override
    public void drawRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        slave.drawRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void fillRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        slave.fillRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawOval(int i, int i1, int i2, int i3) {
        slave.drawOval(i, i1, i2, i3);
    }

    @Override
    public void fillOval(int i, int i1, int i2, int i3) {
        slave.fillOval(i, i1, i2, i3);
    }

    @Override
    public void drawArc(int i, int i1, int i2, int i3, int i4, int i5) {
        slave.drawArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void fillArc(int i, int i1, int i2, int i3, int i4, int i5) {
        slave.fillArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawPolyline(int[] ints, int[] ints1, int i) {
        slave.drawPolyline(ints, ints1, i);
    }

    @Override
    public void drawPolygon(int[] ints, int[] ints1, int i) {
        slave.drawPolygon(ints, ints1, i);
    }

    @Override
    public void fillPolygon(int[] ints, int[] ints1, int i) {
        slave.fillPolygon(ints, ints1, i);
    }

    @Override
    public void drawString(String s, int i, int i1) {
        slave.drawString(s, i, i1);
    }

    @Override
    public void drawString(String s, float v, float v1) {
        slave.drawString(s, v, v1);
    }

    @Override
    public void drawString(AttributedCharacterIterator attributedCharacterIterator, int i, int i1) {
        slave.drawString(attributedCharacterIterator, i, i1);
    }

    @Override
    public void drawString(AttributedCharacterIterator attributedCharacterIterator, float v, float v1) {
        slave.drawString(attributedCharacterIterator, v, v1);
    }

    @Override
    public void drawGlyphVector(GlyphVector glyphVector, float v, float v1) {
        slave.drawGlyphVector(glyphVector, v, v1);
    }

    @Override
    public void fill(Shape shape) {
        slave.fill(shape);
    }

    @Override
    public boolean hit(Rectangle rectangle, Shape shape, boolean b) {
        return slave.hit(rectangle, shape, b);
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return slave.getDeviceConfiguration();
    }

    @Override
    public void setComposite(Composite composite) {
        slave.setComposite(composite);
    }

    @Override
    public void setPaint(Paint paint) {
        slave.setPaint(paint);
    }

    @Override
    public void setStroke(Stroke stroke) {
        slave.setStroke(stroke);
    }

    @Override
    public void setRenderingHint(RenderingHints.Key key, Object o) {
        slave.setRenderingHint(key, o);
    }

    @Override
    public Object getRenderingHint(RenderingHints.Key key) {
        return slave.getRenderingHint(key);
    }

    @Override
    public void setRenderingHints(Map<?, ?> map) {
        slave.setRenderingHints(map);
    }

    @Override
    public void addRenderingHints(Map<?, ?> map) {
        slave.addRenderingHints(map);
    }

    @Override
    public RenderingHints getRenderingHints() {
        return slave.getRenderingHints();
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, ImageObserver imageObserver) {
        return slave.drawImage(image, 1, i1, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, ImageObserver imageObserver) {
        return slave.drawImage(image, i, i1, i2, i3, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, Color color, ImageObserver imageObserver) {
        return slave.drawImage(image, i, i1, color, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, Color color, ImageObserver imageObserver) {
        return slave.drawImage(image, i, i1, i2, i3, color, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, ImageObserver imageObserver) {
        return slave.drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, imageObserver);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, Color color, ImageObserver imageObserver) {
        return slave.drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, color, imageObserver);
    }

    @Override
    public void dispose() {
        slave.dispose();
    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y) {
        slave.drawChars(data, offset, length, x, y);
    }
}
