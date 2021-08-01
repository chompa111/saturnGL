package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.location.Location;
import graphical.basics.location.Point;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import java.awt.*;

public final class CircleBuilder {
    ColorHolder colorHolder;
    Location center;
    NumberHolder radio;

    private CircleBuilder() {
    }

    public static CircleBuilder aCircle() {
        return new CircleBuilder();
    }

    public CircleBuilder withColor(ColorHolder colorHolder) {
        this.colorHolder = colorHolder;
        return this;
    }

    public CircleBuilder withColor(Color color) {
        this.colorHolder = new ColorHolder(color);
        return this;
    }

    public CircleBuilder withCenter(Location center) {
        this.center = center;
        return this;
    }

    public CircleBuilder withCenter(double x, double y) {
        this.center = new Point(x, y);
        return this;
    }

    public CircleBuilder withRadius(NumberHolder radio) {
        this.radio = radio;
        return this;
    }

    public CircleBuilder withRadius(double radio) {
        this.radio = new DoubleHolder(radio);
        return this;
    }

    public Circle build() {
        return new Circle(center != null ? center : new Point(500, 500), radio != null ? radio : new DoubleHolder(100), colorHolder != null ? colorHolder.getColor() : Color.white);

    }
}
