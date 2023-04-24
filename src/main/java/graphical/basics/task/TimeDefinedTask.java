package graphical.basics.task;

import graphical.basics.presentation.Presentation;

public interface TimeDefinedTask extends Task{
    default Task forSeconds(double seconds){
        return forFrames(Presentation.staticReference.seconds(seconds));
    }

    Task forFrames(int frames);
}
