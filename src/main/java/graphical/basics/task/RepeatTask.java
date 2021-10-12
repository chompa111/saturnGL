package graphical.basics.task;

public class RepeatTask implements Task {

    Task task;
    int times;

    int countTimes;

    public RepeatTask(int times, Task task) {
        this.task = task;
        this.times = times;
    }

    @Override
    public void setup() {
        countTimes = 0;
        task.setup();
    }

    @Override
    public void step() {
        task.step();
        if (task.isDone() && (countTimes < times)) {
            task.setup();
            countTimes++;
        }
    }

    @Override
    public boolean isDone() {
        return task.isDone() && countTimes==times;
    }
}
