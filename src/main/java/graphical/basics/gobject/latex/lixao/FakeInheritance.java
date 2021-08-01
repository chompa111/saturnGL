package graphical.basics.gobject.latex.lixao;

import org.scilab.forge.jlatexmath.Box;
import org.scilab.forge.jlatexmath.CharFont;
import org.scilab.forge.jlatexmath.FontInfo;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class FakeInheritance {

    static Box box;


    //caso 1

//    public void drawChar(Box box, Graphics2D g2, float x, float y) {
//
//        // drawDebug(g2, x, y);
//        AffineTransform at = g2.getTransform();
//        g2.translate(x, y);
//        Font font = FontInfo.getFont(getOnBox(box, "cf", CharFont.class).fontId);
//        var size=getOnBox(box, "size", Float.class);
//        if (Math.abs(size - TeXFormula.FONT_SCALE_FACTOR) > TeXFormula.PREC) {
//            g2.scale(size / TeXFormula.FONT_SCALE_FACTOR,
//                    size / TeXFormula.FONT_SCALE_FACTOR);
//        }
//
//        if (g2.getFont() != font) {
//            g2.setFont(font);
//        }
//
//        arr[0] = cf.c;
//        g2.drawChars(arr, 0, 1, 0, 0);
//        g2.setTransform(at);
//    }


    public static void setOnBox(Box box, Object o, String name) {
        try {
            var field = Box.class.getDeclaredField(name);
            field.setAccessible(true);
            field.set(box, o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static <T> T getOnBox(Box box, String name, Class<T> clazz) {
        try {
            var field = Box.class.getDeclaredField(name);
            field.setAccessible(true);
            return (T) field.get(box);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
