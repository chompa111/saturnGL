package graphical.basics.task;

public interface Task {

    public void setup();

    public void step();

    public boolean isDone();

    default Task andThen(Task t2) {
        if (this instanceof SequenceTask) {
            ((SequenceTask) this).addTask(t2);
            return this;
        } else {
            return new SequenceTask(this, t2);
        }
    }

    default Task parallel(Task t2) {
        if (this instanceof ParalelTask) {
            ((ParalelTask) this).addTask(t2);
            return this;
        } else {
            return new ParalelTask(this, t2);
        }
    }

    default Task repeat(int times) {
        return new RepeatTask(times, this);
    }

    default Task wait(int steps) {
        return this.andThen(new WaitTask(steps));
    }

    static void consume(Task task) {
        task.setup();
        while (!task.isDone()) {
            task.step();
        }
    }

}
