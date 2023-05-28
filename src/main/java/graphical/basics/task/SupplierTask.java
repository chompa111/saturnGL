package graphical.basics.task;

import java.util.function.Supplier;

public class SupplierTask implements Task {

    private Task slaveTask;

    public Supplier<?extends Task> taskSupplier;

    public SupplierTask(Supplier<? extends Task> taskSupplier) {
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

    public static SupplierTask of(Supplier<? extends Task> t) {
        return new SupplierTask(t);
    }
}
