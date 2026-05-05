package graphical.basics.triggers;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class OnceTrigger<T> extends Trigger<T> {

    public void evaluate() {
        var x = condition.get();
        if (x != null) {
            if (x instanceof Boolean b) {
                if (b) {
                    action.accept(x);
                    unBind();
                }
            } else {
                action.accept(x);
                unBind();
            }
        }
    }

    public OnceTrigger(Supplier<T> condition, Consumer<T> action) {
        super(condition, action);
    }
}
