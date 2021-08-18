package graphical.basics.presentation.effects;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.DynamicPath;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.presentation.Presentation;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorListTranform;
import graphical.basics.task.transformation.gobject.PositionListTransform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class T3b1b {

    public static enum TransformationType {
        DEFAULT,
        BEST_MATCHING
    }

    private static final Presentation presentation = Presentation.staticReference;

    public static Task turnInto(Gobject a, Gobject b, int steps) {
        ShapeGobject2 sa = null;
        ShapeGobject2 sb = null;
        if (a instanceof ShapeGobject2) {
            sa = (ShapeGobject2) a;
        }
        if (b instanceof ShapeGobject2) {
            sb = (ShapeGobject2) b;
        }
        var dpa = new DynamicPath(sa.getShape(), sa.getFillColorHolder() == null ? null : sa.getFillColorHolder().copy(), sa.getStrokeColorHolder() == null ? null : sa.getStrokeColorHolder().copy());
        var dpb = new DynamicPath(sb.getShape(), sb.getFillColorHolder() == null ? null : sb.getFillColorHolder().copy(), sb.getStrokeColorHolder() == null ? null : sb.getStrokeColorHolder().copy());

        presentation.add(dpa);

        //equalizing number of vertex;
        var sizea = dpa.getRefereceLocations().size();
        var sizeb = dpb.getRefereceLocations().size();

        if (sizea < sizeb) {
            dpa.addPoints(sizeb - sizea);
        } else {
            dpb.addPoints(sizea - sizeb);
        }

        var demorf = new PositionListTransform(dpa.getRefereceLocations(), dpb.getRefereceLocations(), steps);
        var colorpart = new ColorListTranform(dpa.getColors(), dpb.getColors().stream().map(ColorHolder::getColor).collect(Collectors.toList()), steps);
        return demorf.parallel(colorpart)
                .step(() -> presentation.remove(dpa));

    }

    public static Task t3b1b(Gobject a, Gobject b, int steps) {
        return t3b1b(a, b, TransformationType.DEFAULT, steps);
    }

    public static Task t3b1b(Gobject a, Gobject b, TransformationType transformationType, int steps) {

        presentation.remove(a);
        List<ShapeGobject2> la = asShapeList(a);
        List<ShapeGobject2> lb = asShapeList(b);

        List<Task> taskList = new ArrayList<>();

        if (TransformationType.DEFAULT.equals(transformationType)) {
            if (la.size() > lb.size()) {

                // a tem mais elementos

                for (int i = 0; i < lb.size(); i++) {
                    taskList.add(turnInto(la.get(i), lb.get(i), steps));
                }
                for (int i = lb.size(); i < la.size(); i++) {

                    int randomIndexB = (int) (Math.random() * lb.size());

                    taskList.add(turnInto(la.get(i), lb.get(randomIndexB), steps));
                }


            } else {

                // b tem mais elementos
                for (int i = 0; i < la.size(); i++) {
                    taskList.add(turnInto(la.get(i), lb.get(i), steps));
                }

                int diff = lb.size() - la.size();
                int count = 0;
                if (diff != 0) {
                    BOB:
                    while (true) {
                        for (int i = 0; i < la.size(); i++) {
                            taskList.add(turnInto(la.get(i), lb.get(la.size() + count), steps));
                            count++;
                            if (count == diff) break BOB;
                        }
                    }
                }

            }
        }

        if (TransformationType.BEST_MATCHING.equals(transformationType)) {
            if (la.size() > lb.size()) {

                // a tem mais elementos

                for (int i = 0; i < lb.size(); i++) {
                    taskList.add(turnInto(la.get(i), lb.get(i), steps));
                }

                // best matching
                for (int i = lb.size(); i < la.size(); i++) {
                    var objA = la.get(i);
                    var midA = objA.getBorders().midPoint();
                    var objectB = lb.stream().min(Comparator.comparing(xb -> midA.distanceTo(xb.getBorders().midPoint()))).orElse(lb.get(0));
                    taskList.add(turnInto(objA, objectB, steps));
                }


            } else {

                // b tem mais elementos
                for (int i = 0; i < la.size(); i++) {
                    taskList.add(turnInto(la.get(i), lb.get(i), steps));
                }
                for (int i = la.size(); i < lb.size(); i++) {
                    var objB = lb.get(i);
                    var midB = objB.getBorders().midPoint();
                    var objectA = la.stream().min(Comparator.comparing(xa -> midB.distanceTo(xa.getBorders().midPoint()))).orElse(lb.get(0));
                    taskList.add(turnInto( objectA,objB, steps));
                }

            }
        }


        return new

                ParalelTask(taskList).

                parallel(new WaitTask(steps - 1).

                        step(() -> presentation.add(b)));

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
