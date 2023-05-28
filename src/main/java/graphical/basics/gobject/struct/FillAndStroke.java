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

    Stroke stroke = new Stroke();

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
        return stroke.getStrokeThickness();
    }

    public void setStrokeThickness(NumberHolder strokeThickness) {
        stroke.setStrokeThickness(strokeThickness);
    }

    public ColorHolder getFillColorHolder() {
        return fillColorHolder;
    }

    public Color getFillColor() {
        return fillColorHolder.getColor();
    }
    public Color getStrokeColor() {
        return strokeColorHolder.getColor();
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

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    @Override
    protected void copyBasicFields(Gobject copy, Gobject source) {
        super.copyBasicFields(copy, source);
        if (copy instanceof FillAndStroke && source instanceof FillAndStroke) {
            var copyfs = (FillAndStroke) copy;
            var sourcefs = (FillAndStroke) source;

            copyfs.setStroke(sourcefs.getStroke().copy());
            if (sourcefs.getStrokeColorHolder() != null)
                copyfs.setStrokeColorHolder(new ColorHolder(sourcefs.getStrokeColorHolder().getColor()));
            copyfs.setFillColorHolder(new ColorHolder(sourcefs.getFillColorHolder().getColor()));
        }
    }
}
