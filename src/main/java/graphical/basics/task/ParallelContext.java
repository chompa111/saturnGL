package graphical.basics.task;

import graphical.basics.presentation.AnimationContext;

public class ParallelContext implements AnimationContext{

    Thread t;
    private  Runnable context;
    private final Object globalLock = new Object();
    private Runnable lockBehavior;

    public void setContext(Runnable context){
        this.context = context;
        t = new Thread(context);
    }

    public void executeP(Task t) {
        synchronized (globalLock){
            globalLock.notify();
        }
        Object lock = new Object();
        synchronized (lock) {
            t.afterConclusion(() -> {
                synchronized (lock) {
                    lock.notify();
                }
            }).executeInBackGround();
            try {
                lock.wait(); // Pause execution and wait for the lock to be notified
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run(){
        lockBehavior=addBehavior(()->{
            synchronized (globalLock){
                try {
                    globalLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
       t.start();
    }
}
