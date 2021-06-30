package graphical.basics.gobject.latex.lixao;

import graphical.basics.gobject.latex.lixao.Gambeta;
import org.scilab.forge.jlatexmath.Box;

import java.awt.*;
import java.util.LinkedList;

public class BoxProxy extends Box {

    Box slave;

    public BoxProxy(Box slave) {
        this.slave = slave;
        this.width = slave.getWidth();
        this.height = slave.getHeight();
        this.shift = slave.getShift();
        this.depth = slave.getDepth();
        this.type = (int) Gambeta.getOnBox(slave,"type");
        this.children=(LinkedList<Box>)Gambeta.getOnBox(slave,"children");
        System.out.println("");
    }

    @Override
    public void draw(Graphics2D g2, float x, float y) {
        ///
        slave.draw(g2, x, y);
    }

    @Override
    public int getLastFontId() {
        return slave.getLastFontId();
    }

    public void setParent(Box parent) {
        this.parent = parent;
        slave.setParent(parent);
    }

    public Box getParent() {
        return slave.getParent();
    }

    public void setElderParent(Box elderParent) {
        this.elderParent = elderParent;
        slave.setElderParent(elderParent);
    }

    public Box getElderParent() {
        return slave.getElderParent();
    }

    /**
     * Get the width of this box.
     *
     * @return the width of this box
     */
    public float getWidth() {
        return width;
    }

    public void negWidth() {
        width = -width;
        slave.negWidth();
    }

    /**
     * Get the height of this box.
     *
     * @return the height of this box
     */
    public float getHeight() {
        return slave.getHeight();
    }

    /**
     * Get the depth of this box.
     *
     * @return the depth of this box
     */
    public float getDepth() {
        return slave.getDepth();
    }

    /**
     * Get the shift amount for this box.
     *
     * @return the shift amount
     */
    public float getShift() {
        return slave.getWidth();
    }

    /**
     * Set the width for this box.
     *
     * @param w the width
     */
    public void setWidth(float w) {
        slave.setWidth(w);
        width=w;
    }

    /**
     * Set the depth for this box.
     *
     * @param d the depth
     */
    public void setDepth(float d) {
        slave.setDepth(d);
        depth=d;
    }

    public void setHeight(float h) {
        slave.setHeight(h);
        height=h;
    }

    public void setShift(float s) {
        slave.setShift(s);
        shift=s;
    }


}
