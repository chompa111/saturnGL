package presentation;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Polygon;
import graphical.basics.location.Point;
import graphical.basics.presentation.Effects;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.gobject.*;
import graphical.basics.task.WaitTask;
import graphical.basics.value.DoubleHolder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LogoEx extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(60).setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {
        var logo = new SVGGobject("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\src\\main\\resources\\saturn-font-logo.svg");

        var planeta = logo.getGroup("planeta");
        var resto= logo.getGroupExcept("planeta");

        //add(planeta);
        add(logo);
        Effects.init(logo).execute();

        //planeta.transform(resto).execute();


//        var planeta= logo.getGroup("planeta");
//        add(planeta);
//
//        planeta.changeSetPosition(0,-700);
////
//        var planetaCaindo = planeta.move(0, 750).parallel(Effects.fadeIn(planeta))
//                .andThen(planeta.move(0, -60, seconds(0.6))
//                        .andThen(planeta.move(0, 10, seconds(0.6)))
//                );
//
//        planetaCaindo.execute();
//
//        var resto = logo.getGroupExcept("planeta");
//        add(resto);
//
//        Effects.init(resto).execute();
//
//
//        logo.getGroup("satName")
//                .toGroupGobject()
//                .onChildren(x -> {
//                    var originalColor = x.getColors().get(0).getColor();
//                    return x.changeColor(new Color(240, 180, 30, 200), seconds(0.5))
//                            .andThen(x.changeColor(originalColor, seconds(0.5)));
//                }, 1).execute();


//        var groupplanet = new Group((ArrayList) planet.getShapeGobjects());
//
//        group.onChildren(x -> {
//            var original = x.getColors().get(0).getColor();
//            return x.move(0, 50, seconds(1)).andThen(x.move(0, -50));
//        }, 1).parallel(
//                new WaitTask(20).andThen(groupplanet.move(0, 50).andThen(groupplanet.move(0, -70)).andThen(groupplanet.move(0,15,seconds(0.5))))).execute();


//
//        group.onChildren(x -> {
//            var original = x.getColors().get(0).getColor();
//            x.getColors().get(0).setColor(new Color(0, 0, 0, 0));
//            return x.changeColor(original, seconds(2));
//        },1).parallel(
//
//                group.onChildren(x -> {
//                    var y = (ShapeGobject) x;
//                    var perc = new DoubleHolder(0);
//                    var sw = new StrokeWriter(y.getShape(), perc);
//                    add(sw);
//
//                    return perc.aceleratedChange(1, seconds(2))
//                            .parallel(sw.changeColor(new Color(0, 0, 0, 0), seconds(2.5)));
//
//                },1)).execute();


        //
//        group.onChildren(x -> {
//            var original = x.getColors().get(0).getColor();
//            return x.changeColor(new Color(200, 160, 0), seconds(0.5)).andThen(x.changeColor(original, seconds(0.5)));
//        }, 1).repeat(2).execute();

//        group.onChildren(x->{
//            return x.move(0,-50).andThen(x.move(0,50));
//        },1).execute();
//

//        var list = new ArrayList<Task>();
//
//
//        batik.getShapeGobjects().forEach(x -> {
//            list.add( new CodeTask(() -> {
//                var seg = SegmentBoy.asSegments(x.getShape());
//                for (Gobject line : seg.getGobjects()) {
//                    add(line);
//                    CodeTask.doStep();
//                }
//            }));
//        });

//        var group = new Group((ArrayList) batik.getShapeGobjects());
//
//        group.onChildren(x -> {
//            var original = x.getColors().get(0).getColor();
//            return x.move(0, 100, seconds(1)).andThen(x.move(0, -100, seconds(1)));
//        }, 1).execute();


//
//        batik.getShapeGobjects().forEach(x -> {
//            var xx=(Math.random() * 300)-150;
//            var yy=(Math.random() * 300)-150;
//            x.changeSetPosition(xx, yy);
//            var color=x.getColors().get(0).getColor();
//            x.getColors().get(0).setColor(new Color(0,0,0,0));
//            list.add(x.move(-xx,-yy,seconds(3)).parallel(x.changeColor(color,seconds(3))));
//        });
//
//        new ParalelTask(list).execute();
//
//        var list2=new ArrayList<Task>();
//        var batikgobjects=batik.getShapeGobjects();
//        for (int i=0;i<batikgobjects.size();i++){
//            var x= batik.getShapeGobjects().get(i);
//            var color=x.getColors().get(0).getColor();
//
//            list2.add(new WaitTask(i+1).andThen(x.changeColor(Color.yellow,seconds(0.5)).andThen(x.changeColor(color,seconds(0.5)))));
//        }
//        new ParalelTask(list2).execute();
//

        new WaitTask(1).execute();

        cut();
    }

    public static void main(String[] args) {
        new LogoEx().buildPresentation();
    }
}
