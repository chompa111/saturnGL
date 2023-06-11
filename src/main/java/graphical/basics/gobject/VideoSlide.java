package graphical.basics.gobject;

import graphical.basics.presentation.AnimationStaticReference;
import graphical.basics.task.InterruptableTask;

public class VideoSlide implements SaturnSlide {
    Video mainVideo;
    Video revertVideo;

    InterruptableTask videoTask;

    public VideoSlide(String path) {
        this.mainVideo = new Video(path);
        mainVideo.resize();
        mainVideo.setPositionTo(AnimationStaticReference.staticReference.midScreen());
    }

    @Override
    public void perform() {
        AnimationStaticReference.staticReference.add(mainVideo);
        videoTask = mainVideo.play()
                .executeInBackGround();
    }

    @Override
    public void revert() {
        AnimationStaticReference.staticReference.remove(mainVideo);
        if (revertVideo == null) return;
        AnimationStaticReference.staticReference.add(revertVideo);
        mainVideo.play()
                .executeInBackGround();
    }


    @Override
    public void remove() {
        AnimationStaticReference.staticReference.remove(mainVideo);
        videoTask.kill();
        mainVideo.seek();
    }

    @Override
    public void add() {
        AnimationStaticReference.staticReference.add(mainVideo);
    }

    @Override
    public void release() {
        AnimationStaticReference.staticReference.remove(mainVideo);
        videoTask.kill();
        mainVideo.startOver();
    }

}
