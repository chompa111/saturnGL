package graphical.basics.task.transformation.gobject;

import graphical.basics.location.Location;
import graphical.basics.task.Task;

import java.util.List;

public class PositionListTransform implements Task {

    List<Location> locationsFrom;
    List<Location> locationsTo;

    int steps;
    int stepCount;

    double[] aX;
    double[] aY;

    double[] deltaX;
    double[] deltaY;


    public PositionListTransform(List<Location> locationsFrom, List<Location> locationsTo, int steps) {
        this.steps = steps;
        this.locationsFrom = locationsFrom;
        this.locationsTo = locationsTo;
    }

    @Override
    public void setup() {
        stepCount = 0;
        aX = new double[locationsFrom.size()];
        aY = new double[locationsFrom.size()];
        deltaX = new double[locationsFrom.size()];
        deltaY = new double[locationsFrom.size()];

        for (int i = 0; i < locationsFrom.size(); i++) {
            var amountX = locationsTo.get(i).getX() - locationsFrom.get(i).getX();
            var amountY = locationsTo.get(i).getY() - locationsFrom.get(i).getY();
            aX[i] = (4 * amountX) / (2 * steps + (steps * steps));
            aY[i] = (4 * amountY) / (2 * steps + (steps * steps));
        }
    }

    @Override
    public void step() {

        if (stepCount < (steps / 2)) {

            for (int i = 0; i < locationsFrom.size(); i++) {
                deltaX[i] += aX[i];
                deltaY[i] += aY[i];
                locationsFrom.get(i).incrementX(deltaX[i]);
                locationsFrom.get(i).incrementY(deltaY[i]);
            }

        } else {

            for (int i = 0; i < locationsFrom.size(); i++) {
                locationsFrom.get(i).incrementX(deltaX[i]);
                locationsFrom.get(i).incrementY(deltaY[i]);
                deltaX[i] -= aX[i];
                deltaY[i] -= aY[i];
            }

        }

        stepCount++;
    }

    @Override
    public boolean isDone() {
        return stepCount == steps;
    }
}
