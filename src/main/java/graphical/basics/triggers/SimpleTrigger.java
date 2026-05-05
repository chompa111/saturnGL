package graphical.basics.triggers;

import java.util.function.Consumer;
import java.util.function.Supplier;

public  class SimpleTrigger<T> {
    Supplier<T> condition;
    Consumer<T> action;

    public void evaluate() {
        var x = condition.get();
        if (x != null) action.accept(x);
    }

    public SimpleTrigger(Supplier<T> condition, Consumer<T> action) {
        this.condition = condition;
        this.action = action;
    }
}

