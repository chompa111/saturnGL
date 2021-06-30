package codec;

import graphical.basics.gobject.Gobject;
import graphical.basics.gobject.GroupGobject;
import graphical.basics.gobject.Img;
import graphical.basics.gobject.latex.lixao.Latex;
import graphical.basics.location.Point;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.MorfTransform;

import java.awt.*;
import java.util.List;

public class Exp10 extends Presentation {

    @Override
    public void buildPresentation() {
        var igunaa = new Img(new Point(200, 200), "/prof.png");
     //   var bman=  new Img(new Point(0, 0), "/bman2.png");

        List<Gobject> latex2 = Latex.generateExp("\\left(\\[ \\int_{a}^{b} x^2 \\,dx \\]\\right)+f(x)^{s+sin(\\theta)}",200,500, Color.white);

        var latex = new GroupGobject(latex2);


        execute(new MorfTransform(igunaa,latex,120));
        cut();
    }

    public static void main(String[] args) {
        new Exp10().buildPresentation();
    }
}
