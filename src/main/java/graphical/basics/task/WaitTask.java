package graphical.basics.task;

public class WaitTask implements Task {

    int steps;
    int stepCount;

    public WaitTask(int steps) {
        this.steps = steps;

    }

    @Override
    public void setup() {
        stepCount = 0;
    }

    @Override
    public void step() {
        stepCount++;
    }

    @Override
    public boolean isDone() {
        return stepCount == steps;
    }
}
