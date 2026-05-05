package graphical.basics.triggers;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TriggerAfterFrames<T> extends Trigger<T> {
    final int framesToTrigger;
    int framesCount = 0;

    public TriggerAfterFrames(Supplier<T> condition, Consumer<T> action, int frames) {
        super(condition, action);
        framesToTrigger = frames;
    }

    public void evaluate() {
        var conditionTriggered = condition.get();

        if (conditionTriggered == null) {
            framesCount = 0;
            return;
        }

        if (framesCount == framesToTrigger) {
            framesCount = 0;
            action.accept(conditionTriggered);
        }
        framesCount++;

    }
}
