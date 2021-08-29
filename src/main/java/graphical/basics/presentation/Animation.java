package graphical.basics.presentation;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.DynamicPath;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.Line;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.gobject.struct.StrokeGobject;
import graphical.basics.location.Point;
import graphical.basics.presentation.effects.T3b1b;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.gobject.ColorListTranform;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.ColorTranform2;
import graphical.basics.task.transformation.gobject.PositionListTransform;
import graphical.basics.value.DoubleHolder;

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
            var color=colorHolder.getColor();
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
            return ((Group) gobject).onChildren(x -> Animation.strokeAndFill(x, steps),0.5);
        }

        if (gobject instanceof SVGGobject) {
            return strokeAndFill(((SVGGobject) gobject).toGroupGobject(), steps);
        }

        return fadeIn(gobject, steps);
    }

    public static Task strokeAndFill(Gobject gobject){
        return strokeAndFill(gobject, presentation.seconds(1));
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

    public static Task emphasize(Gobject gobject,Color color) {
        return new ContextSetupTask(() -> {
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


    public static Task t3b1b(Gobject a, Gobject b, int steps){
        return T3b1b.t3b1b(a,b,steps);
    }

    public static Task t3b1b(Gobject a, Gobject b, T3b1b.TransformationType transformationType, int steps){
        return T3b1b.t3b1b(a,b,transformationType,steps);
    }

    public static Task redBarr(Gobject g, int steps) {
        var bounds = g.getBorders();
        var line = new Line(bounds.getL1(), bounds.getL2(), Color.red);
        line.setStrokeThickness(new DoubleHolder(3));
        return strokeAndFill(line, steps);
    }

    public static Task redX(Gobject g, int steps){
        var bounds = g.getBorders();

        var line1 = new Line(bounds.getL1(), bounds.getL2(), new Color(180,20,0));
        line1.setStrokeThickness(new DoubleHolder(3));

        var line2 = new Line(bounds.l1plusWidth(), bounds.l2minusWidth(), new Color(180,20,0));
        line2.setStrokeThickness(new DoubleHolder(3));

        return strokeAndFill(line1, steps).parallel(new WaitTask(presentation.seconds(0.10)).andThen(strokeAndFill(line2, steps)));
    }
}