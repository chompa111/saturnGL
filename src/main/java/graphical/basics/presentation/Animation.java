package graphical.basics.presentation;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.LatexGobject;
import graphical.basics.gobject.Polygon;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.gobject.struct.StrokeGobject;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorListTranform;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.ColorTranform2;
import graphical.basics.task.transformation.gobject.PositionListTransform;
import org.w3c.dom.ls.LSInput;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Animation {

    private static final Presentation presentation = Presentation.staticReference;


    public static Task fadeOut(Gobject gobject, int steps) {
        return new ColorTranform(gobject, new Color(0, 0, 0, 0), steps);
    }

    public static Task fadeOut(Gobject gobject) {
        return new ColorTranform(gobject, new Color(0, 0, 0, 0), presentation.seconds(1));
    }

    public static Task fadeIn(Gobject gobject, int steps) {

        var colorHolders = gobject.getColors();
        var beforeColors = ColorHolder.toColorList(colorHolders);
        for (ColorHolder colorHolder : colorHolders) {
            colorHolder.setColor(new Color(0, 0, 0, 0));
        }

        return new ColorListTranform(colorHolders, beforeColors, steps);
    }

    public static Task fadeIn(Gobject gobject) {
        return fadeIn(gobject, presentation.seconds(1));
    }


    public static Task strokeAndFill(Gobject gobject, int steps) {
        if (gobject instanceof ShapeGobject2) {
            var sw = new StrokeGobject((ShapeGobject2) gobject);
            presentation.add(sw);
            return sw.getPerc().change(1, steps)
                    .andThen(fadeIn(gobject, steps).parallel(fadeOut(sw, steps)))
                    .step(() -> presentation.remove(sw));
        }
        if (gobject instanceof ShapeLike) {
            var sw = new StrokeGobject(((ShapeLike) gobject).asShapeGobject());
            presentation.add(sw);
            return sw.getPerc().change(1, steps)
                    .andThen(fadeIn(gobject, steps).parallel(fadeOut(sw, steps)))
                    .step(() -> presentation.remove(sw));

        }

        if (gobject instanceof Group) {
            return ((Group) gobject).onChildren(x -> Animation.strokeAndFill(x, steps));
        }

        if (gobject instanceof SVGGobject) {
            return strokeAndFill(((SVGGobject) gobject).toGroupGobject(), steps);
        }

        return fadeIn(gobject, steps);
    }


    public static Task fadeInGrow(Gobject gobject, int steps) {
        return new ContextSetupTask(() -> {
            gobject.getScale().setValue(0);
            return fadeIn(gobject, steps).parallel(gobject.getScale().change(1, steps));
        });
    }

    public static Task emphasize(Gobject gobject) {
        return new ContextSetupTask(() -> {
            var colorHolders = gobject.getColors();
            var beforeColors = ColorHolder.toColorList(colorHolders);

            var backToOriginalColor = new ColorListTranform(gobject.getColors(), beforeColors, presentation.seconds(0.5));

            return new ColorTranform2(gobject, Color.yellow, 1.5, presentation.seconds(0.5))
                    .parallel(gobject.getScale().change(0.1, presentation.seconds(0.5)))
                    .andThen(backToOriginalColor.parallel(gobject.getScale().change(-0.1, presentation.seconds(0.5))));
        });
    }

    public static Task wooble(Gobject gobject) {
        return gobject.getAngle().change(0.5, presentation.seconds(0.5))
                .andThen(gobject.getAngle().change(-1.0, presentation.seconds(0.5))
                        .andThen(gobject.getAngle().change(0.5, presentation.seconds(0.5))));
    }


    public static Task turnInto(Gobject a, Gobject b, int steps) {
        ShapeGobject2 sa = null;
        ShapeGobject2 sb = null;
        if (a instanceof ShapeGobject2) {
            sa = (ShapeGobject2) a;
        }
        if (b instanceof ShapeGobject2) {
            sb = (ShapeGobject2) b;
        }
        Polygon pa = new Polygon(sa.getShape(), sa.getFillColorHolder()!=null? sa.getFillColorHolder().copy():sa.getStrokeColorHolder().copy());
        Polygon pb = new Polygon(sb.getShape(),sb.getFillColorHolder()!=null? sb.getFillColorHolder().copy():sb.getStrokeColorHolder().copy());
        presentation.add(pa);

        //equalizing number of vertex;
        var sizea = pa.getRefereceLocations().size();
        var sizeb = pb.getRefereceLocations().size();
        if (sizea < sizeb) {
            pa.addPoints(sizeb - sizea);
        } else {
            pb.addPoints(sizea - sizeb);
        }

        var demorf = new PositionListTransform(pa.getRefereceLocations(), pb.getRefereceLocations(), steps);
        var colorpart = new ColorListTranform(pa.getColors(), pb.getColors().stream().map(ColorHolder::getColor).collect(Collectors.toList()), steps);

        return demorf.parallel(colorpart)
                .andThen(() -> fadeOut(pa, presentation.seconds(0.5)))
                .step(() -> {
                    presentation.remove(pa);
                });
    }

//    public static Task turnInto(LatexGobject a, Gobject b, int steps) {
//
//        presentation.remove(a);
//        //var sg=new ShapeGobject2(a.toSingleShape(),Color.white,null);
//        List<Task> taskList = new ArrayList<>();
//
//        return new ParalelTask(a.getGobjects().stream().map(x -> turnInto(x, b, steps)).collect(Collectors.toList()));
//
//    }

    public static Task peperain(Gobject a, Gobject b, int steps) {

        presentation.remove(a);
        List<ShapeGobject2> la = asShapeList(a);
        List<ShapeGobject2> lb = asShapeList(b);

        List<Task> taskList = new ArrayList<>();

        if (la.size() > lb.size()) {
            for (int i = 0; i < lb.size(); i++) {
                taskList.add(turnInto(la.get(i), lb.get(i), steps));
            }
            for (int i = lb.size(); i < la.size(); i++) {

                int randomIndexB = (int) (Math.random() * lb.size());

                taskList.add(turnInto(la.get(i), lb.get(randomIndexB), steps));
            }
        } else {
            for (int i = 0; i < la.size(); i++) {
                taskList.add(turnInto(la.get(i), lb.get(i), steps));
            }

            int diff=lb.size()-la.size();
            int count=0;

            BOB:while(true){
                for(int i=0;i<la.size();i++){
                    taskList.add(turnInto(la.get(i), lb.get(la.size()+count), steps));
                    count++;
                    if(count==diff)break BOB;
                }
            }
        }

        return new ParalelTask(taskList)
                .parallel(new WaitTask(presentation.seconds(0.7)).andThen(() -> {
                            presentation.add(b);
                            return fadeIn(b, presentation.seconds(0.5));
                        }
                ));
        // .step(()->presentation.add(b));

    }


    private static List<ShapeGobject2> asShapeList(Gobject g) {

        if (g instanceof ShapeGobject2) {
            return Arrays.asList((ShapeGobject2) g);
        }

        if (g instanceof ShapeLike) {
            var auxShape = ((ShapeLike) g).asShapeGobject();
            return Arrays.asList(auxShape);
        }
        if (g instanceof Group) {
            List<ShapeGobject2> list = new ArrayList<>();
            for (Gobject gobject : ((Group) g).getGobjects()) {
                list.addAll(asShapeList(gobject));
            }
            return list;
        }
        throw new RuntimeException("não foi possivel conveter o objeto:" + g.getClass() + ", que não é Shape, ShapeLike ou Coleção/Grupo");
    }
}
