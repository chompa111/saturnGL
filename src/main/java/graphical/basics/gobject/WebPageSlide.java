package graphical.basics.gobject;

import graphical.basics.examples.WebPageEmbeder;
import graphical.basics.presentation.AnimationStaticReference;
import graphical.basics.presentation.RTAnimation;

public class WebPageSlide implements SaturnSlide {
    WebPageEmbeder embeder;
    String url;

    public WebPageSlide(String url) {
        this.url = url;

    }

    @Override
    public void perform() {
        var animFrame = RTAnimation.staticReference.getAnimationFrame();
        animFrame.disableScreenUpdate();
        embeder = new WebPageEmbeder(animFrame.getFrame(), url);
    }

    @Override
    public void revert() {

    }

    @Override
    public void remove() {
        var animFrame = RTAnimation.staticReference.getAnimationFrame();
        animFrame.enableScreenUpdate();
        embeder.releaseFrame();
    }

    @Override
    public void add() {

    }

    @Override
    public void release() {

    }
}
