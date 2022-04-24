package graphical.basics.task;

import java.util.*;

public class ParalelTask implements Task {
    List<Task> taskList;
    List<Task> tasks;

    public ParalelTask(Task... tasks) {
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    public ParalelTask(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    @Override
    public void setup() {
        taskList = new ArrayList<>(tasks);
        for (Task task : taskList) {
            task.setup();
        }
    }

    @Override
    public void step() {
        //TODO problemas de desempenho
        Set<Task> toRemove = new HashSet<>();
        for (Task task : taskList) {
            if (task.isDone()) {
                toRemove.add(task);
            } else {
                task.step();
            }
        }
        toRemove.forEach(task -> taskList.remove(task));
    }

    @Override
    public void shutDown() {
        taskList = new ArrayList<>(tasks);
        for (Task task : taskList) {
            task.shutDown();
        }
    }

    @Override
    public boolean isDone() {
        return taskList.isEmpty();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
