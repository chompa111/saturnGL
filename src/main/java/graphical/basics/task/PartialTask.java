package graphical.basics.task;

public class PartialTask implements Task {
    int counter = 0;
    int steps;
    Task task;

    public PartialTask(int steps, Task task) {
        this.steps = steps;
        this.task = task;
    }

    @Override
    public void setup() {
        counter = 0;
        task.setup();
    }

    @Override
    public void step() {
        task.step();
        counter++;
    }

    @Override
    public boolean isDone() {
        return task.isDone() || counter >= steps;
    }


    public Task remainingTask(){
        if(task.isDone()){
            // NO OP
            return new Task(){
                @Override
                public void setup() {

                }

                @Override
                public void step() {

                }

                @Override
                public boolean isDone() {
                    return true;
                }
            };
        }
        return new StartedTask(task);
    }
}
