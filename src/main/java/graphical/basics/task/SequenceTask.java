package graphical.basics.task;

import java.util.*;

public class SequenceTask implements Task {

    List<Task> taskList;
    List<Task> tasks;
    Set<Task> setup = new HashSet<>();

    public SequenceTask(Task... tasks) {
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    public SequenceTask(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    @Override
    public void setup() {
        taskList = new ArrayList<>(tasks);
//        for (var task : taskList) task.setup();
    }

    @Override
    public void step() {

        var task = taskList.get(0);
        if (!setup.contains(task)) {
            setup.add(task);
            task.setup();
        }
        task.step();
        //TODO problemas de desempenho
        if (task.isDone()) {

            taskList.remove(task);
            task.shutDown();
            if (taskList.size() != 0){
                setup = new HashSet<>();
                task.setup();
            }
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
