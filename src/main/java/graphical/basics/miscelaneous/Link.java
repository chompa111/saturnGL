package graphical.basics.miscelaneous;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Line;
import graphical.basics.gobject.StringGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.SupplierTask;
import graphical.basics.task.Task;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class Link extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
    }

    @Override
    protected void buildAnimation() {

        var counter = new StringGobject("a");
        counter.setPositionTo(Location.at(800,100));
        add(counter);
        counter.addBehavior(()->counter.set("g:"+getGobjects().size()));

        tree(5, Location.at(500, 100)).execute();
        var node = CircleBuilder.aCircle()
                .withCenter(Location.at(500, 500))
                .withRadius(12)
                .withColor(ColorHolder.randomColor())
                .build();
    }

    Task tree(int profund, Location root) {
        var nodeTask = SupplierTask.of(() -> {
            var node = CircleBuilder.aCircle()
                    .withCenter(root)
                    .withRadius(12)
                    .withColor(ColorHolder.randomColor())
                    .build();
            add(node);
            return Animations.strokeAndFill(node);
        });

        if (profund == 0) {
            return nodeTask;
        }

        return SupplierTask.of(() -> {
            var pointL = root.plus(-50 / Math.pow(2, 3 - profund), 100);
            var pointR = root.plus(50 / Math.pow(2, 3 - profund), 100 );

            var lineL = new Line(root, pointL, Color.white, new DoubleHolder(3));
            var lineR = new Line(root, pointR, Color.white, new DoubleHolder(3));

            add(lineR, lineL);
            var branchL = tree(profund - 1, pointL);
            var branchR = tree(profund - 1, pointR);

            return nodeTask.andThen(Animations.strokeAndFill(lineL)
                    .parallel(Animations.strokeAndFill(lineR)))
                    .andThen(branchL.parallel(branchR));
        });
    }

    public static void main(String[] args) {
        new Link().build();
    }
}
