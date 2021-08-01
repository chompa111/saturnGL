package graphical.basics.presentation;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.*;
import graphical.basics.gobject.Polygon;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.shape.StrokeWriterV2;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.SVGGobject2;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.gobject.struct.StrokeGobject3;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorListTranform;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.ColorTranform2;
import graphical.basics.task.transformation.gobject.PositionListTransform;
import graphical.basics.value.DoubleHolder;
import presentation.SVGGobject;

import java.awt.*;
import java.util.ArrayList;

public class Effects {

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

    public static Task init(Gobject gobject) {
        if (gobject instanceof SVGGobject2) {
            var svgGobject = (SVGGobject2) gobject;
            var group = new Group((ArrayList) svgGobject.getShapeGobjects());
            var writeTask = group.onChildren(x -> {
                return new ContextSetupTask(() -> {
                    var y = (ShapeGobject2) x;

                    // var sw = new StrokeWriter(y.getShape(), y.getRefereceLocations().get(0), perc);
                    var sw = new StrokeGobject3(y, Color.white);
                    var perc = sw.getPerc();
                    presentation.add(sw);

                    return perc.change(1, presentation.seconds(1))
                            .andThen(sw.changeColor(new Color(0, 0, 0, 0), presentation.seconds(1)))
                            .afterStep(() -> presentation.remove(sw));
                });
            }, 1);
            return (writeTask.parallel(new WaitTask(presentation.seconds(1)).andThen(fadeIn(gobject))));
        } else if (gobject instanceof SVGGobject) {

            var svgGobject = (SVGGobject) gobject;
            var group = new Group((ArrayList) svgGobject.getShapeGobjects());
            var writeTask = group.onChildren(x -> {
                return new ContextSetupTask(() -> {
                    var y = (ShapeGobject) x;

                    // var sw = new StrokeWriter(y.getShape(), y.getRefereceLocations().get(0), perc);
                    var sw = new StrokeWriterV2(y.getShape(), Color.white);
                    var perc = sw.getPerc();
                    presentation.add(sw);

                    return perc.change(1, presentation.seconds(1))
                            .andThen(sw.changeColor(new Color(0, 0, 0, 0), presentation.seconds(1)))
                            .afterStep(() -> presentation.remove(sw));
                });
            }, 1);
            return (writeTask.parallel(new WaitTask(presentation.seconds(1)).andThen(fadeIn(gobject))));
        } else if (gobject instanceof ShapeLike) {
            return new ContextSetupTask(() -> {
                var shapeGobject = (ShapeLike) gobject;

                var sw = new StrokeWriterV2(shapeGobject.asShape(), gobject.getColors().get(0).getColor());
                presentation.add(sw);
                var perc = sw.getPerc();
                return perc.change(1, presentation.seconds(1))
                        .parallel(new WaitTask(presentation.seconds(0.5)).andThen(fadeIn(gobject, presentation.seconds(1.0))))
                        .andThen(fadeOut(sw, presentation.seconds(0.5)))
                        .afterStep(() -> presentation.remove(sw));
            });
        } else if (gobject instanceof Group) {
            return ((Group) gobject).onChildren(Effects::init);
        } else if (gobject instanceof ShapeGobject) {

            return new ContextSetupTask(() -> {
                var shapeGobject = (ShapeGobject) gobject;
                var perc = new DoubleHolder(0);
                var sw = new StrokeWriter(shapeGobject.getShape(), shapeGobject.getRefereceLocations().get(0), perc);
                presentation.add(sw);

                return perc.change(1, presentation.seconds(1))
                        //  .parallel(sw.changeColor(new Color(0, 0, 0, 0), presentation.seconds(1)))
                        .parallel(fadeIn(shapeGobject))
                        .afterStep(() -> presentation.remove(sw));
            });

        } else if (gobject instanceof ShapeGobject2) {

            return new ContextSetupTask(() -> {
                var shapeGobject = (ShapeGobject2) gobject;

                var sw = new StrokeGobject3((ShapeGobject2) gobject, shapeGobject.getFillColorHolder() != null ? shapeGobject.getFillColorHolder().getColor() : shapeGobject.getStrokeColorHolder().getColor());
                var perc = sw.getPerc();
                presentation.add(sw);

                return perc.change(1, presentation.seconds(1))
                        .andThen(fadeIn(gobject).parallel(new WaitTask(presentation.seconds(0.5)).andThen(fadeOut(sw, presentation.seconds(0.5)))))
                        .afterStep(() -> presentation.remove(sw));
            });

        }
        return fadeIn(gobject);
    }

    public static Task formTransform(Gobject g1, Gobject g2) {
        return new ContextSetupTask(() -> {
            Polygon poly1 = null;
            Polygon poly2 = null;

            if (g1 instanceof ShapeGobject) {
                ShapeGobject shapeGobject = (ShapeGobject) g1;
                var color = shapeGobject.getColorHolderFill() != null ? shapeGobject.getColorHolderFill() : shapeGobject.getColorHolderStroke();
                if (color == null) color = new ColorHolder(new Color(0, 0, 0, 0));
                poly1 = new Polygon((((ShapeGobject) g1).getShape()), color);
            }
            if (g2 instanceof ShapeGobject) {
                ShapeGobject shapeGobject = (ShapeGobject) g2;
                var color = shapeGobject.getColorHolderFill() != null ? shapeGobject.getColorHolderFill() : shapeGobject.getColorHolderStroke();
                if (color == null) color = new ColorHolder(new Color(0, 0, 0, 0));
                poly2 = new Polygon((((ShapeGobject) g2).getShape()), color);
            }

            if (g1 instanceof ShapeLike) {
                var shape1 = ((ShapeLike) g1).asShape();
                poly1 = new Polygon(shape1, g1.getColors().get(0));
            }
            if (g2 instanceof ShapeLike) {
                var shape2 = ((ShapeLike) g2).asShape();
                poly2 = new Polygon(shape2, g2.getColors().get(0));
            }
            if (g1 instanceof Polygon) {
                poly1 = (Polygon) g1;
            }
            if (g2 instanceof Polygon) {
                poly2 = (Polygon) g2;
            }


            if (poly1.getNumOfEdges() > poly2.getNumOfEdges()) {
                int edgesToAdd = poly1.getNumOfEdges() - poly2.getNumOfEdges();
                poly2.addPoints(edgesToAdd);
            }
            if (poly2.getNumOfEdges() > poly1.getNumOfEdges()) {
                int edgesToAdd = poly2.getNumOfEdges() - poly1.getNumOfEdges();
                poly1.addPoints(edgesToAdd);
            }

            presentation.remove(g1);
            presentation.add(poly1);
            // presentation.add(poly2);

            return new PositionListTransform(poly1.getRefereceLocations(), poly2.getRefereceLocations(), presentation.seconds(1));
        });
    }

    public static Task formTransform(SVGGobject svgGobject, Gobject gobject) {
        return svgGobject.toGroupGobject().onChildren(x -> formTransform(x, gobject));
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

    public static Task singleRedLine(Gobject gobject) {
        return new ContextSetupTask(() -> {
            var borders = gobject.getBorders();
            Line l1 = new Line(borders.getL1(), borders.getL2(), Color.red);
            presentation.add(l1);
            return l1.init().andThen(new ContextSetupTask(() -> fadeOut(l1))
            ).afterStep(() -> {
                presentation.remove(l1);
            });
        });
    }


}
