package graphical.basics.task;

public class StartedTask implements Task{

    Task slave;

    public StartedTask(Task slave) {
        this.slave = slave;
    }

    @Override
    public void setup() {
       // nothing
    }

    @Override
    public void step() {
        slave.step();
    }

    @Override
    public boolean isDone() {
        return slave.isDone();
    }
}
