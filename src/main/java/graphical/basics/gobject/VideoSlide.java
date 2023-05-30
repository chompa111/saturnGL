package graphical.basics.gobject;

import graphical.basics.presentation.AnimationStaticReference;

public class VideoSlide implements SaturnSlide {
    Video mainVideo;
    Video revertVideo;

    public VideoSlide(Video mainVideo) {
        this.mainVideo = mainVideo;
    }

    @Override
    public void perform() {
        AnimationStaticReference.staticReference.add(mainVideo);
        mainVideo.play(AnimationStaticReference.staticReference.seconds(3))
                .executeInBackGround();
    }

    @Override
    public void revert() {
        AnimationStaticReference.staticReference.remove(mainVideo);
        if (revertVideo == null) return;
        AnimationStaticReference.staticReference.add(revertVideo);
        mainVideo.play(AnimationStaticReference.staticReference.seconds(3))
                .executeInBackGround();
    }



    @Override
    public void remove() {
        AnimationStaticReference.staticReference.remove(mainVideo);
    }

    @Override
    public void add() {
        AnimationStaticReference.staticReference.add(mainVideo);
    }

    @Override
    public void release(){
        AnimationStaticReference.staticReference.remove(mainVideo);
        mainVideo.startOver();
    }

}
