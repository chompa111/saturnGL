package graphical.basics.miscelaneous;

import graphical.basics.gobject.CodeBlock;
import graphical.basics.presentation.AnimationStaticReference;

import java.util.HashMap;

public abstract class CodeRunner {

    CodeBlock codeBlock;
   public HashMap<Integer, String> evaluator = new HashMap<>();
    final Object lock = new Object();

    public CodeRunner(CodeBlock codeBlock) {
        this.codeBlock = codeBlock;
    }

    abstract void code();



    void runCode() {
        new Thread(this::code).start();
    }

    void line(int number) {
        codeBlock.setDebuglineAnimated(number, AnimationStaticReference.staticReference.seconds(0.3)+1).executeInBackGround();
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void release() {
        synchronized (lock) {
            lock.notify();
        }
    }

    public void evaluate(int line, String s) {
        evaluator.put(line, s);
    }
}
