package graphical.basics.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EndLessParallelTask implements Task {

    List<Task> taskList;

    Set<Task> setedUpTasks;

    public EndLessParallelTask() {
        this.taskList = new ArrayList<>();
        this.setedUpTasks = new HashSet<>();
    }

    @Override
    public void setup() {
        // nothing
    }

    @Override
    public void step() {
        Set<Task> toRemove = new HashSet<>();
        for (Task task : taskList) {
            if (!setedUpTasks.contains(task)) {
                task.setup();
                setedUpTasks.add(task);
            }
            if (task.isDone()) {
                toRemove.add(task);
            } else {
                task.step();
            }
        }
        toRemove.forEach(task -> {
            taskList.remove(task);
            setedUpTasks.remove(task);
        });
    }

    @Override
    public boolean isDone() {
        return false;
    }

    public InterruptableTask append(Task task) {
        var interruptableTask = new InterruptableTask(task);
        taskList.add(interruptableTask);
        return interruptableTask;
    }

    public Task getResumedTask() {
        var list = new ArrayList<Task>();
        for (Task task : taskList) {
            if (!setedUpTasks.contains(task)) {
                setedUpTasks.add(task);
                task.setup();
            }
            list.add(task);
        }
        return new ParalelTask(list);
    }

    public boolean hasTasks() {
        return !taskList.isEmpty();
    }

    public void clear(){
        taskList= new ArrayList<>();
    }
}
