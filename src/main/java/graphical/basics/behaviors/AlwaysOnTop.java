package graphical.basics.behaviors;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.presentation.Presentation;

public class AlwaysOnTop {

    public static Runnable onTop(Gobject gobject) {
        return () ->{
            Presentation.staticReference.remove(gobject);
            Presentation.staticReference.add(gobject);
        };
    }
}
