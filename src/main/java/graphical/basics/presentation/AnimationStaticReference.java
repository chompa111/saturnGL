package graphical.basics.presentation;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.task.InterruptableTask;
import graphical.basics.task.Task;

import java.util.Arrays;
import java.util.function.Consumer;

public abstract class AnimationStaticReference {
    public static AnimationStaticReference staticReference = null;

    public abstract int seconds(double seconds);

    public abstract void execute(Task task);

    public abstract InterruptableTask executeInBackGround(Task task);

    public abstract <T> Runnable addBehavior(T metadata, Consumer<T> task);

    public abstract void removeBehavior(Runnable r);

    public abstract Runnable addBehavior(Runnable task);

    public abstract void add(Gobject gobject);

    public abstract void remove(Gobject gobject);

    public abstract void clear();

    public abstract PresentationConfig getPresentationConfig();

    public Location midScreen() {
        var config = getPresentationConfig();
        return Location.at(config.getWidth() / 2.0, config.getHeight() / 2.0);
    }
}
