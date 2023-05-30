package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.Animation;
import graphical.basics.task.Task;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static graphical.basics.gobject.latex.Latex.generateExp;

public class TextGobject extends Group {

    public TextGobject(String latex, Location location, Color color) {
        var list = generateExp(latex, location, color);
        this.addAll(list);
    }

    public TextGobject(Font font, String s, Location location, Color color) {
        var list = generateText(font, s, location, color);
        this.addAll(list);
    }


    public static java.util.List<Gobject> generateText(Font font, String s, Location location, Color color) {

        List<Gobject> gobjects = new ArrayList<>();
        var gv = font.createGlyphVector(new FontRenderContext(null, true, true), s);

        for (int i = 0; i < s.length(); i++) {
            var c = s.charAt(i);
            if(c==' ')continue;
            var sh = gv.getGlyphOutline(i);
            var t = new AffineTransform();
            t.translate(location.getX(), location.getY());
            sh = t.createTransformedShape(sh);
            //gobjects.add(new Char2(font, new Point(sh.getBounds().x , sh.getBounds().y ), new char[]{c}, font.getSize(), color));
            gobjects.add(new ShapeGobject2(sh, new ColorHolder(color), null));
        }

        return gobjects;

    }

    public static void indexsize(Group group) {
        int index = 0;
        for (Gobject gobject : group.getGobjects()) {

            var number = new TextGobject(new Font("Consolas", Font.BOLD, 15), index + "", gobject.getBorders().midPoint(), Color.green);
            Animation.staticReference.add(number);
            index++;
        }
    }

    public static void colorizeOrder(List<Gobject> l1, List<Gobject> l2) {


        for (int i = 0; i < Math.min(l1.size(), l2.size()); i++) {
            var color = ColorHolder.randomColor();
            l1.get(i).changeColor(color, 4).execute();
            l2.get(i).changeColor(color, 4).execute();
        }
    }


    public Shape toSingleShape() {
        Path2D path = new Path2D.Float();
        for (Shape shape : this.getGobjects().stream().map(x -> (ShapeGobject2) x).map(ShapeGobject2::getShape).collect(Collectors.toList())) {
            path.append(shape, false);
        }
        return path;
    }

    public Task write(){
        return this.onChildren(Animations::strokeAndFill,3);
    }
    public Task write(int frames, double delay){
        return this.onChildren(c-> Animations.strokeAndFill(c,frames),delay);
    }
}
