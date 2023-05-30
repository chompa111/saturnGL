package graphical.basics.task;

public class InterruptableTask implements Task {

    private final Task slave;
    private boolean interrupted=false;
    private boolean killed=false;

    public InterruptableTask(Task slave) {
        this.slave = slave;
    }

    @Override
    public void setup() {
        slave.setup();
    }

    @Override
    public void step() {
        if (!interrupted) {
            slave.step();
        }
    }

    @Override
    public boolean isDone() {
        return killed||slave.isDone();
    }


    public void kill(){
        killed=true;
    }
    public void pause(){
        interrupted=true;
    }
    public void resume(){
        interrupted=false;
    }
}
