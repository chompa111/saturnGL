package graphical.basics.gobject;

import graphical.basics.location.Location;
import graphical.basics.location.Point;

import java.awt.*;

import static graphical.basics.gobject.latex.lixao.Latex.generateExp;

public class LatexGobject extends Group{

    public LatexGobject(String latex, Location location, Color color) {
        var list =generateExp(latex, location, color);
        this.addAll(list);
    }
}
