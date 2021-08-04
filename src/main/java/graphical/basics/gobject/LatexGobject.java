package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Char2;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.location.Location;
import graphical.basics.location.Point;
import graphical.basics.presentation.Presentation;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import static graphical.basics.gobject.latex.lixao.Latex.generateExp;

public class LatexGobject extends Group {

    public LatexGobject(String latex, Location location, Color color) {
        var list = generateExp(latex, location, color);
        this.addAll(list);
    }

    public LatexGobject(Font font, String s, Location location, Color color) {
        var list = generateText(font, s, location, color);
        this.addAll(list);
    }


    public static java.util.List<Gobject> generateText(Font font, String s, Location location, Color color) {

        List<Gobject> gobjects = new ArrayList<>();
        var gv = font.createGlyphVector(new FontRenderContext(null, true, true), s);

        for (int i = 0; i < s.length(); i++) {
            var sh = gv.getGlyphOutline(i);
            var c = s.charAt(i);
            var t = new AffineTransform();
            t.translate(location.getX(), location.getY());
            sh = t.createTransformedShape(sh);
            //gobjects.add(new Char2(font, new Point(sh.getBounds().x , sh.getBounds().y ), new char[]{c}, font.getSize(), color));
            gobjects.add(new ShapeGobject2(sh,new ColorHolder(color),null));
        }

        return gobjects;

    }

    public static void indexsize(Group group){
        int index=0;
        for (Gobject gobject:group.getGobjects()){

            var number= new LatexGobject(new Font("Consolas",Font.BOLD,15),index+"",gobject.getBorders().midPoint(), Color.green);
            Presentation.staticReference.add(number);
            index++;
        }
    }
}
