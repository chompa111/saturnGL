package graphical.basics.task;

import java.util.*;

public class SequenceTask implements Task {

    List<Task> taskList;
    List<Task> tasks;

    public SequenceTask(Task... tasks) {
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    @Override
    public void setup() {
        taskList = new ArrayList<>(tasks);
        taskList.get(0).setup();
    }

    @Override
    public void step() {
        taskList.get(0).step();
        //TODO problemas de desempenho
        if (taskList.get(0).isDone()) {
            taskList.remove(taskList.get(0));
            if (taskList.size() != 0)
                taskList.get(0).setup();
        } else {
          //  taskList.get(0).step();
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
