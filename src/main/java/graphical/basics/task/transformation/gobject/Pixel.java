package graphical.basics.task.transformation.gobject;

import graphical.basics.ColorHolder;

import java.awt.*;

public class Pixel {

    ColorHolder colorHolder;

    double x;
    double y;

    public Pixel(Color color, double x, double y) {
        this.colorHolder = new ColorHolder(color);
        this.x = x;
        this.y = y;
    }

    public ColorHolder getColorHolder() {
        return colorHolder;
    }

    public void setColorHolder(ColorHolder colorHolder) {
        this.colorHolder = colorHolder;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void paint(Graphics g) {
        g.setColor(colorHolder.getColor());
        g.drawLine((int) x, (int) y, (int) x, (int) y);
    }

    public Pixel clone() {
        return new Pixel(colorHolder.getColor(), x, y);
    }

}
