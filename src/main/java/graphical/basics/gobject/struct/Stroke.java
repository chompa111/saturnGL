package graphical.basics.gobject.struct;

import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import java.awt.*;

public class Stroke {

    NumberHolder strokeThickness = new DoubleHolder(1);

    NumberHolder dashPhase = new DoubleHolder(0);

    NumberHolder dashWidth = new DoubleHolder(15);


    boolean enableDash = false;

    public java.awt.Stroke getStroke() {

        var pattern = !enableDash ? null : new float[]{(float) dashWidth.getValue(), (float) dashWidth.getValue()};

        return new BasicStroke((float) this.strokeThickness.getValue(),
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 100, pattern, (float) dashPhase.getValue());
    }

    public void enableDash() {
        enableDash = true;
    }

    public void disableDash() {
        enableDash = false;
    }

    public NumberHolder getStrokeThickness() {
        return strokeThickness;
    }

    public NumberHolder getDashPhase() {
        return dashPhase;
    }

    public NumberHolder getDashWidth() {
        return dashWidth;
    }

    public void setStrokeThickness(NumberHolder strokeThickness) {
        this.strokeThickness = strokeThickness;
    }


    public Stroke copy() {
        var copy = new Stroke();
        copy.getStrokeThickness().setValue(strokeThickness.getValue());
        copy.getDashPhase().setValue(strokeThickness.getValue());
        copy.getDashWidth().setValue(dashWidth.getValue());
        return copy;
    }
}
