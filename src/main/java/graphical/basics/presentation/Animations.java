package graphical.basics.presentation;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.Line;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.*;
import graphical.basics.presentation.effects.T3b1b;
import graphical.basics.task.*;
import graphical.basics.task.transformation.gobject.ColorListTranform;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.ColorTranform2;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class Animations {


    private static final AnimationStaticReference presentation = AnimationStaticReference.staticReference;

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
            var color = colorHolder.getColor();
            colorHolder.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0));
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
            return ((Group) gobject).onChildren(x -> Animations.strokeAndFill(x, steps), 0.7);
        }

        if (gobject instanceof SVGGobject) {
            return strokeAndFill(((SVGGobject) gobject).toGroupGobject(), steps);
        }

        return fadeIn(gobject, steps);
    }


    public static Task unstrokeAndUnFill(Gobject gobject, int steps) {
        if (gobject instanceof ShapeGobject2) {
            var sw = new StrokeGobject((ShapeGobject2) gobject);
            sw.getPerc().setValue(1);
            presentation.add(sw);

            return fadeOut(gobject, steps)
                    .andThen(sw.getPerc().change(-1, steps))
                    .step(() -> presentation.remove(sw));
        }
        if (gobject instanceof ShapeLike) {
            var sw = new StrokeGobject(((ShapeLike) gobject).asShapeGobject());
            sw.getPerc().setValue(1);
            presentation.add(sw);

            return fadeOut(gobject, steps)
                    .andThen(sw.getPerc().change(-1, steps))
                    .step(() -> presentation.remove(sw));

        }

        if (gobject instanceof Group) {
            return ((Group) gobject).onChildren(x -> Animations.unstrokeAndUnFill(x, steps), 0.7);
        }

        if (gobject instanceof SVGGobject) {
            return unstrokeAndUnFill(((SVGGobject) gobject).toGroupGobject(), steps);
        }

        return fadeOut(gobject, steps);
    }


    public static Task strokeAndFill(Gobject gobject) {
        return strokeAndFill(gobject, presentation.seconds(1));
    }


    public static Task fadeInGrow(Gobject gobject, int steps) {
        return new SupplierTask(() -> {
            var scale = gobject.scale.getValue();
            gobject.getScale().setValue(0);
            return fadeIn(gobject, steps).parallel(gobject.getScale().change(scale, steps));
        });
    }

    public static Task fadeInGrowFromBig(Gobject gobject, int steps) {
        return new SupplierTask(() -> {
            var scale = gobject.scale.getValue();
            gobject.getScale().setValue(15);
            return fadeIn(gobject, steps).parallel(gobject.getScale().changeTo(scale, steps));
        });
    }


    public static Task fadeoutGrow(Gobject gobject, int steps) {
        return fadeOut(gobject, steps).parallel(gobject.getScale().change(gobject.getScale().getValue() * -1, steps));
    }

    public static Task emphasize(Gobject gobject) {
        return new SupplierTask(() -> {
            var colorHolders = gobject.getColors();
            var beforeColors = ColorHolder.toColorList(colorHolders);

            var backToOriginalColor = new ColorListTranform(gobject.getColors(), beforeColors, presentation.seconds(0.5));

            return new ColorTranform2(gobject, Color.yellow, 1.5, presentation.seconds(0.5))
                    .parallel(gobject.getScale().change(0.1, presentation.seconds(0.5)))
                    .andThen(backToOriginalColor.parallel(gobject.getScale().change(-0.1, presentation.seconds(0.5))));
        });
    }

    public static Task emphasize(Gobject gobject, Color color) {
        return new SupplierTask(() -> {
            var colorHolders = gobject.getColors();
            var beforeColors = ColorHolder.toColorList(colorHolders);

            var backToOriginalColor = new ColorListTranform(gobject.getColors(), beforeColors, presentation.seconds(0.5));

            return new ColorTranform2(gobject, color, 1.5, presentation.seconds(0.5))
                    .parallel(gobject.getScale().change(0.1, presentation.seconds(0.5)))
                    .andThen(backToOriginalColor.parallel(gobject.getScale().change(-0.1, presentation.seconds(0.5))));
        });
    }


    public static Task wooble(Gobject gobject) {
        return gobject.getAngle().change(0.5, presentation.seconds(0.5))
                .andThen(gobject.getAngle().change(-1.0, presentation.seconds(0.5))
                        .andThen(gobject.getAngle().change(0.5, presentation.seconds(0.5))));
    }

    public static Task wooble2(Gobject gobject) {
        return gobject.getAngle().change(0.5).forSeconds(0.5)
                .andThen(gobject.getAngle().change(-1.0).forSeconds(0.5)
                        .andThen(gobject.getAngle().change(0.5).forSeconds(0.5)));
    }


    public static Task t3b1b(Gobject a, Gobject b, int steps) {
        return T3b1b.t3b1b(a, b, steps);
    }

    public static Task t3b1b(Gobject a, Gobject b, T3b1b.TransformationType transformationType, int steps) {
        return T3b1b.t3b1b(a, b, transformationType, steps);
    }

    public static Task redBarr(Gobject g, int steps) {
        var bounds = g.getBorders();
        var line = new Line(bounds.getL1(), bounds.getL2(), Color.red);
        line.setStrokeThickness(new DoubleHolder(3));
        return strokeAndFill(line, steps);
    }

    public static Task redX(Gobject g, int steps) {
        var bounds = g.getBorders();

        var line1 = new Line(bounds.getL1(), bounds.getL2(), new Color(180, 20, 0));
        line1.setStrokeThickness(new DoubleHolder(3));

        var line2 = new Line(bounds.l1plusWidth(), bounds.l2minusWidth(), new Color(180, 20, 0));
        line2.setStrokeThickness(new DoubleHolder(3));

        return strokeAndFill(line1, steps).parallel(new WaitTask(presentation.seconds(0.10)).andThen(strokeAndFill(line2, steps)));
    }

    public static Task clipInit(Gobject gobject) {
        var borders = gobject.getBorders();
        var clipBox = new ClipBox(borders.getL1().copy(), borders.getL2().copy());
        presentation.remove(gobject);
        presentation.add(clipBox);
        clipBox.add(gobject);
        gobject.changeSetPosition(0, -borders.getheight());

        return gobject.move(0, borders.getheight()).andThen(new SingleStepTask(() -> {
            presentation.remove(clipBox);
            presentation.add(gobject);
        })).parallel(fadeIn(gobject));
    }

    public static Task replace(Gobject replaced, Gobject newGObject) {
        return new SupplierTask(() -> {
            newGObject.setPositionTo(replaced.getMidPoint());
            return t3b1b(replaced, newGObject, presentation.seconds(1));
        });
    }

    public static Task emphasizeBox(Gobject g) {
        var rect = Rect.backgroundFor(g, 15);
        rect.setStrokeColorHolder(new ColorHolder(Color.orange));
        rect.setFillColorHolder(new ColorHolder(new Color(0, 0, 0, 0)));
        return strokeAndFill(rect, presentation.seconds(1));
    }

}