package graphical.basics.task;

import codec.Presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    default void execute() {
        Presentation.staticReference.execute(this);
    }

//
//    public static <T extends Task> Collector<T, ?, ParalelTask> toList() {
//        return new Collectors.CollectorImpl(ArrayList::new, List::add, (left, right) -> {
//            left.addAll(right);
//            return left;
//        }, CH_ID);
//    }

}
