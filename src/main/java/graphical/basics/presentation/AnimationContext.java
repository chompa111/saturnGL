package graphical.basics.presentation;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.task.InterruptableTask;
import graphical.basics.task.Task;

import java.util.function.Consumer;

public interface AnimationContext {
    default int seconds(double seconds) {
        return AnimationStaticReference.staticReference.seconds(seconds);
    }

    default void execute(Task task) {
        AnimationStaticReference.staticReference.execute(task);
    }

    default InterruptableTask executeInBackGround(Task task) {
        return AnimationStaticReference.staticReference.executeInBackGround(task);
    }

    default <T> Runnable addBehavior(T metadata, Consumer<T> task) {
        return AnimationStaticReference.staticReference.addBehavior(metadata, task);
    }

    default void removeBehavior(Runnable r) {
        AnimationStaticReference.staticReference.removeBehavior(r);
    }

    default Runnable addBehavior(Runnable task) {
        return AnimationStaticReference.staticReference.addBehavior(task);
    }

    default void add(Gobject gobject) {
        AnimationStaticReference.staticReference.add(gobject);
    }

    default void remove(Gobject gobject) {
        AnimationStaticReference.staticReference.remove(gobject);
    }

    default void clear() {
        AnimationStaticReference.staticReference.clear();
    }

    default PresentationConfig getPresentationConfig() {
        return AnimationStaticReference.staticReference.getPresentationConfig();
    }

    default Location midScreen() {
        var config = getPresentationConfig();
        return Location.at(config.getWidth() / 2.0, config.getHeight() / 2.0);
    }
}
