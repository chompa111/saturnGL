package graphical.basics.task;

public class ShutDownTask implements Task {

    Runnable runnable;
    Task slaveTask;

    public ShutDownTask(Runnable runnable, Task slaveTask) {
        this.runnable = runnable;
        this.slaveTask = slaveTask;
    }

    @Override
    public void setup() {
        slaveTask.setup();
    }

    @Override
    public void step() {
        slaveTask.step();
    }

    @Override
    public boolean isDone() {
        return slaveTask.isDone();
    }

    @Override
    public void shutDown() {
        slaveTask.shutDown();
        runnable.run();
    }
}
