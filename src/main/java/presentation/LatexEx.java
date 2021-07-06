package presentation;

import codec.Presentation;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.Ball;
import graphical.basics.gobject.Gobject;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.Polygon;
import graphical.basics.gobject.latex.lixao.Latex;
import graphical.basics.location.Point;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.PositionTransform;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LatexEx extends Presentation {
    @Override
    public void buildPresentation() {
        List<Gobject> latex = Latex.generateExp("a^2+2ab+b^2", 200, 500, Color.white);
        List<Gobject> latex2 = Latex.generateExp("(a+b)^2...", 200, 500, Color.white);
        //latex.forEach(this::add);
        var tasklist = latex.stream().map(c -> new ParalelTask(new PositionTransform(c, (Math.random() * 400) - 200, (Math.random() * 400) - 200, seconds(1)),
                new ColorTranform(c, ColorHolder.randomColor(), seconds(1))
        )).collect(Collectors.toList());
//        List<Task> g= new ArrayList(tasklist);
//        execute(new ParalelTask(g));
        var gg = new Group(latex);
        //   add(new Pepe());
        //var gg2 = new Group(latex2);

        latex.forEach(this::add);
        var v1 = new Polygon(new Color(0, 164, 240),
                438.31479, 623.91436,
                271.17811, 486.8414,
                532.52385, 406.6807,
                438.31479, 623.91436
        );

        var ball = new Ball(new Point(500, 500), Color.red);
        //add(gg);
        new WaitTask(seconds(0.5)).execute();

        var tasklist2 = new ArrayList<Task>();
        for(int i=0;i<latex.size()&& i<latex2.size();i++){
            tasklist2.add(latex.get(i).transform(latex2.get(i),seconds(1)));
        }

        new ParalelTask(tasklist2).execute();

//        for (int i = 0; i < 2; i++) {
//            execute(new MorfTransform(v1, gg2, seconds(1)));
//            execute(new MorfTransform(gg2, ball, seconds(1)));
//            execute(new MorfTransform(ball, v1, seconds(1)));
//        }

        //  execute(new ColorTranform(latex.get(0), Color.red, seconds(0.5)));
        //execute(new WaitTask(1));
        cut();
    }

    public static void main(String[] args) {
        new LatexEx().buildPresentation();
    }


}
