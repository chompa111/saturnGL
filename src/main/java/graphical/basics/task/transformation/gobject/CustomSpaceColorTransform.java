package graphical.basics.task.transformation.gobject;

import graphical.basics.gobject.struct.Gobject;

import java.awt.*;
import java.util.function.Function;

public class CustomSpaceColorTransform extends ColorSpaceColorTransform {
    private final Function<Color, float[]> colorToColorSpace;
    private final Function<float[], Color> colorSpaceToColor;

    public CustomSpaceColorTransform(Gobject gobject, Color color, Function<Color, float[]> colorToColorSpace,
                                     Function<float[], Color> colorSpaceToColor, int steps) {
        super(gobject, color, steps);
        this.colorToColorSpace = colorToColorSpace;
        this.colorSpaceToColor = colorSpaceToColor;
    }

    @Override
    float[] getColorComponents(Color color) {
        return colorToColorSpace.apply(color);
    }

    @Override
    Color fromColorSpace(float[] colorComponents) {
        return colorSpaceToColor.apply(colorComponents);
    }
}
