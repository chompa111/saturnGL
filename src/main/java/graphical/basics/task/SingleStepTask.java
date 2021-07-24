package graphical.basics.task;

public class SingleStepTask implements Task{

    private final Runnable step;

    boolean hasExecuted = false;

    public SingleStepTask(Runnable step) {
        this.step = step;
    }

    @Override
    public void setup() {
       //nothing
    }

    @Override
    public void afterStep() {
        step.run();
        hasExecuted=true;
    }

    @Override
    public boolean isDone() {
        return hasExecuted;
    }
}
