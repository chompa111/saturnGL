package graphical.basics.presentation;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.gobject.struct.StrokeGobject;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.Task;
import graphical.basics.task.transformation.gobject.ColorListTranform;
import graphical.basics.task.transformation.gobject.ColorTranform;
import graphical.basics.task.transformation.gobject.ColorTranform2;

import java.awt.*;

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


}
