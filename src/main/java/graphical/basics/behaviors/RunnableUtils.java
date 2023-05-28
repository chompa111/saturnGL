package graphical.basics.behaviors;

import java.util.function.Consumer;

public class RunnableUtils {
    public static <T> Runnable wrap(T metadata, Consumer<T> consumer) {
        return () -> consumer.accept(metadata);
    }
}
