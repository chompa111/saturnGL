package graphical.basics.presentation.effects;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.DynamicPath;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.gobject.struct.ShapeGobject;
import graphical.basics.presentation.Animation;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorListTranform;
import graphical.basics.task.transformation.gobject.PositionListTransform;

import java.awt.*;
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

    private static final Animation ANIMATION = Animation.staticReference;

    public static Task turnInto(Gobject a, Gobject b, int steps) {
        ShapeGobject sa = null;
        ShapeGobject sb = null;
        if (a instanceof ShapeGobject) {
            sa = (ShapeGobject) a;
        }
        if (b instanceof ShapeGobject) {
            sb = (ShapeGobject) b;
        }

        //todos os objetos são stroke and fill então vale o caso de colocar cores transparestes para fazer o stroke aparecer ou sumir caso um dos objetos não tenha stroke

        var dpa = new DynamicPath(sa.getShape(), sa.getFillColorHolder() == null ? new ColorHolder(new Color(0, 0, 0, 0)) : sa.getFillColorHolder().copy(), sa.getStrokeColorHolder() == null ? new ColorHolder(new Color(0, 0, 0, 0)) : sa.getStrokeColorHolder().copy());
        var dpb = new DynamicPath(sb.getShape(), sb.getFillColorHolder() == null ? new ColorHolder(new Color(0, 0, 0, 0)) : sb.getFillColorHolder().copy(), sb.getStrokeColorHolder() == null ? new ColorHolder(new Color(0, 0, 0, 0)) : sb.getStrokeColorHolder().copy());

        dpa.setStrokeThickness(sa.getStrokeThickness());
        dpb.setStrokeThickness(sb.getStrokeThickness());

        ANIMATION.add(dpa);

        //equalizing number of vertex;
        var sizeA = dpa.getReferenceLocations().size();
        var sizeB = dpb.getReferenceLocations().size();

        if (sizeA < sizeB) {
            dpa.addPoints(sizeB - sizeA);
        } else if(sizeA > sizeB) {
            dpb.addPoints(sizeA - sizeB);
        }

//        dpa.equalizeNumPoints(dpb);
        var thicknessDiff = dpb.getStrokeThickness().getValue() - dpa.getStrokeThickness().getValue();
        var strokeTask = dpa.getStrokeThickness().change(thicknessDiff, steps);

        var demorf = new PositionListTransform(dpa.getReferenceLocations(), dpb.getReferenceLocations(), steps);
        var colorpart = new ColorListTranform(dpa.getColors(), dpb.getColors().stream().map(ColorHolder::getColor).collect(Collectors.toList()), steps);
        return demorf.parallel(colorpart)
                .parallel(strokeTask)
                .step(() -> ANIMATION.remove(dpa));

    }

    public static Task t3b1b(Gobject a, Gobject b, int steps) {
        return t3b1b(a, b, TransformationType.DEFAULT, steps);
    }

    public static Task t3b1b(Gobject a, Gobject b, TransformationType transformationType, int steps) {
        var gobjectAIndexAux = ANIMATION.getObjectIndex(a);
        var gobjectAIndex = gobjectAIndexAux == -1 ? 0: gobjectAIndexAux;
                ANIMATION.remove(a);
        List<ShapeGobject> la = asShapeList(a);
        List<ShapeGobject> lb = asShapeList(b);

        List<Task> taskList = new ArrayList<>();

        if (TransformationType.DEFAULT.equals(transformationType)) {
            if (la.size() > lb.size()) {

                // a tem mais elementos

                for (int i = 0; i < lb.size(); i++) {
                    taskList.add(turnInto(la.get(i), lb.get(i), steps));
                }


                int diff = la.size() - lb.size();
                int count = 0;
                if (diff != 0) {
                    LBB:
                    while (true) {
                        for (int i = 0; i < lb.size(); i++) {
                            taskList.add(turnInto(la.get((lb.size() - 1) + count), lb.get(i), steps));
                            count++;
                            if (count == diff) break LBB;
                        }
                    }

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
                    taskList.add(turnInto(objectA, objB, steps));
                }

            }
        }


        return new

                ParalelTask(taskList).

                parallel(new WaitTask(steps - 1).

                        step(() -> ANIMATION.add(b, gobjectAIndex)));

    }


    private static List<ShapeGobject> asShapeList(Gobject g) {

        if (g instanceof ShapeGobject) {
            return Arrays.asList((ShapeGobject) g);
        }

        if (g instanceof ShapeLike) {
            var auxShape = ((ShapeLike) g).asShapeGobject();
            return Arrays.asList(auxShape);
        }
        if (g instanceof Group) {
            List<ShapeGobject> list = new ArrayList<>();
            for (Gobject gobject : ((Group) g).getGobjects()) {
                list.addAll(asShapeList(gobject));
            }
            return list;
        }

        if(g instanceof SVGGobject){
           return  ((SVGGobject)g).getShapeGobjects();
        }
        throw new RuntimeException("não foi possivel conveter o objeto:" + g.getClass() + ", que não é Shape, ShapeLike ou Coleção/Grupo");
    }
}
