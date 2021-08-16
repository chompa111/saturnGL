package graphical.basics.gobject.struct;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FillAndStroke extends Gobject {

    protected ColorHolder fillColorHolder;
    protected ColorHolder strokeColorHolder;

    NumberHolder strokeThickness= new DoubleHolder(1);

    public FillAndStroke() {
    }

    public FillAndStroke(Color fillColor, Color strokeColor) {
        this.fillColorHolder = new ColorHolder(fillColor);
        this.strokeColorHolder = new ColorHolder(strokeColor);
    }

    public FillAndStroke(ColorHolder fillColorHolder, ColorHolder strokeColorHolder) {
        this.fillColorHolder = fillColorHolder;
        this.strokeColorHolder = strokeColorHolder;
    }


    @Override
    public List<ColorHolder> getColors() {
        var list = new ArrayList<ColorHolder>();
        if (fillColorHolder != null) {
            list.add(fillColorHolder);
        }
        if (strokeColorHolder != null) {
            list.add(strokeColorHolder);
        }

        return list;
    }

    public NumberHolder getStrokeThickness() {
        return strokeThickness;
    }

    public void setStrokeThickness(NumberHolder strokeThickness) {
        this.strokeThickness = strokeThickness;
    }

    public ColorHolder getFillColorHolder() {
        return fillColorHolder;
    }

    public void setFillColorHolder(ColorHolder fillColorHolder) {
        this.fillColorHolder = fillColorHolder;
    }

    public ColorHolder getStrokeColorHolder() {
        return strokeColorHolder;
    }

    public void setStrokeColorHolder(ColorHolder strokeColorHolder) {
        this.strokeColorHolder = strokeColorHolder;
    }
}
