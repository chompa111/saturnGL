package graphical.basics.behaviors;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.presentation.Animation;

public class AlwaysOnTop {

    public static Runnable onTop(Gobject gobject) {
        return () ->{
            Animation.staticReference.remove(gobject);
            Animation.staticReference.add(gobject);
        };
    }
}
