package graphical.basics;


import graphical.basics.presentation.Presentation;
import graphical.basics.task.Task;
import graphical.basics.task.transformation.gobject.ColorListTranform;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ColorHolder {

    Color color;

    public ColorHolder(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static Color randomColor() {
        return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public static List<Color> toColorList(List<ColorHolder> colorHolderList) {
        return colorHolderList.stream().map(ColorHolder::getColor).collect(Collectors.toList());
    }

    public ColorHolder copy() {
        return new ColorHolder(color);
    }

    public Task changeColor(Color c) {
        return changeColor(c, Presentation.staticReference.seconds(1));
    }

    public Task changeColor(Color color, int steps) {
        return new ColorListTranform(List.of(this),List.of(color),steps);
    }
}
