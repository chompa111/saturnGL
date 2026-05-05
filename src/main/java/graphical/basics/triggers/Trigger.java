package graphical.basics.triggers;

import graphical.basics.presentation.AnimationContext;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Trigger<T> implements AnimationContext {
    Supplier<T> condition;
    Consumer<T> action;

    Runnable r;

    protected void evaluate() {
        var x = condition.get();
        if (x != null) {
            if (x instanceof Boolean b) {
                if (b) {
                    action.accept(x);
                }
            } else {
                action.accept(x);
            }
        }
    }

    public Trigger(Supplier<T> condition, Consumer<T> action) {
        this.condition = condition;
        this.action = action;
    }

    public void bind() {
        if (r != null) {
            System.out.println("trigger already bound");
            return;
        }
        r = this::evaluate;
        addBehavior(r);
    }

    public void unBind() {
        if (r == null) {
            System.out.println("trigger not bound yet");
            return;
        }
        removeBehavior(r);
        r = null;
    }

}

