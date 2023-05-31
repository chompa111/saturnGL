package graphical.basics.behaviors;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.AnimationStaticReference;

public class AlwaysOnTop {

    public static Runnable onTop(Gobject gobject) {
        return () ->{
            AnimationStaticReference.staticReference.remove(gobject);
            AnimationStaticReference.staticReference.add(gobject);
        };
    }
}
