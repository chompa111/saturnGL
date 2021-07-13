package graphical.basics.task;

import java.util.function.Function;
import java.util.function.Supplier;

public class ContextSetupTask implements Task {

    private Task slaveTask;

    public Supplier<Task> taskSupplier;

    public ContextSetupTask(Supplier<Task> taskSupplier) {
        this.taskSupplier = taskSupplier;
    }

    @Override
    public void setup() {
        slaveTask = taskSupplier.get();
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
}
