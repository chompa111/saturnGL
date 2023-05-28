package graphical.basics.task;

import graphical.basics.presentation.AnimationStaticReference;
import graphical.basics.presentation.Presentation;

public interface TimeDefinedTask extends Task{
    default Task forSeconds(double seconds){
        return forFrames(AnimationStaticReference.staticReference.seconds(seconds));
    }

    Task forFrames(int frames);
}
