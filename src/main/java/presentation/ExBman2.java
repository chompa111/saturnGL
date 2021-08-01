package presentation;

import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.gobject.*;
import graphical.basics.gobject.Polygon;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.MorfTransform;

import java.awt.*;

public class ExBman2 extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    public void buildPresentation() {

        var v0 = new Polygon(new Color(214, 104, 243),
                532.52385, 406.6807,
                271.17811, 486.8414,
                418.29465, 404.724,
                532.52385, 406.6807

        );

        var v1 = new Polygon(new Color(203, 64, 240),
                438.31479, 623.91436,
                271.17811, 486.8414,
                532.52385, 406.6807,
                438.31479, 623.91436
        );

        var v2 = new Polygon(new Color(172, 62, 203),
                685.85134, 632.12096,
                438.31479, 623.91436,
                532.52385, 406.6807,
                685.85134, 632.12096

        );

        var v3 = new Polygon(new Color(182, 85, 209),
                685.85134, 632.12096,
                532.52385, 406.6807,
                671.09465, 498.1102,
                685.85134, 632.12096


        );

        var v4 = new Polygon(new Color(131, 42, 156),
                665.47282, 774.06347,
                519.93197, 851.56067,
                685.85134, 632.12096,
                665.47282, 774.06347


        );

        var v5 = new Polygon(new Color(160, 51, 191),
                685.85134, 632.12096,
                519.93197, 851.56067,
                438.31479, 623.91436,
                685.85134, 632.12096


        );
        var v6 = new Polygon(new Color(185, 59, 219),
                519.93197, 851.56067,
                260.06841, 759.83287,
                438.31479, 623.91436,
                519.93197, 851.56067


        );
        var v7 = new Polygon(new Color(195, 36, 237),
                438.31479, 623.91436,
                260.06841, 759.83287,
                271.17811, 486.8414,
                438.31479, 623.91436


        );
        var v8 = new Polygon(new Color(163, 52, 194),
                260.06841, 759.83287,
                246.78091, 627.81876,
                271.17811, 486.8414,
                260.06841, 759.83287

        );
        var v9 = new Polygon(new Color(130, 43, 156),
                519.93197, 851.56067,
                402.32959999999997, 854.6703699999999,
                260.06841, 759.83287,
                519.93197, 851.56067


        );
//        List<Task> taskList = new ArrayList<>();
//
//        var l1 = Arrays.asList(v0, v1, v2, v3, v4, v5, v6, v7, v8, v9);
//        for (Gobject gobject : l1) {
//            double x = Math.random() * 1000 - 500;
//            double y = Math.random() * 1000 - 500;
//            taskList.add(
//                    new PositionTransform(gobject, x, y, seconds(1))
//                            .andThen(new PositionTransform(gobject, -x, -y, seconds(1)))
//            );
//        }
//
        var t1 = new Group(v0, v1, v2, v3, v4, v5, v6, v7, v8, v9);

       // add(new Img(new Point(0,0),""));
        //  add(t1);
//
//        execute(new ParalelTask(taskList));

//        Ball b2 = new Ball(new Point(500, 500), Color.yellow);
//        Ball b3 = new Ball(new Point(400, 500), Color.blue);
//        Ball b4 = new Ball(new Point(450, 700), Color.green);
//        var gg = new GroupGobject(b2, b3, b4);

//        Ball b = new Ball(new Point(500,500),Color.yellow);
//        execute(new MorfTransform(t1,b, seconds(1)));

        add(t1.getGobjects().get(0));
        execute(new WaitTask(seconds(1)));
        for(int i=0;i<t1.getGobjects().size()-1;i++){
            execute(new MorfTransform(t1.getGobjects().get(i), t1.getGobjects().get(i+1), 10));
        }

//        for (int i = 0; i < t1.getGobjects().size(); i++) {
//            for (int j = 0; j < t1.getGobjects().size(); j++) {
//                if (i != j) {
//                    execute(new WaitTask(seconds(1)).andThen(new MorfTransform(t1.getGobjects().get(i), t1.getGobjects().get(j), seconds(0.5))));
//                }
//            }
//        }

        //add(gg);

       // execute(new WaitTask(seconds(1)).andThen(new MorfTransform(v6, gg, 60)));
        execute(new WaitTask(1));
        cut();

    }

    public static void main(String[] args) {
        new ExBman2().buildPresentation();
    }
}
