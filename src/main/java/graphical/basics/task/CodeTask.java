package graphical.basics.task;

import java.util.function.Consumer;

public class CodeTask implements Task {
    Thread thread;

    boolean once = false;


    public CodeTask(Runnable runnable) {
        thread = new Thread(runnable);
    }

    @Override
    public void setup() {

    }

    @Override
    public void step() {
        if (!once) {
            thread.start();
            once = true;
        }
        thread.resume();
    }

    @Override
    public boolean isDone() {
        return once && !thread.isAlive();
    }

    public static void doStep() {
        Thread.currentThread().suspend();
    }

    public static void runTask(Task task){
        task.setup();
        while (!task.isDone()) {
            task.step();
            doStep();
        }
    }
}
