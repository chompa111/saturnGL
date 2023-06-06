package graphical.basics.gobject;

import graphical.basics.presentation.AnimationStaticReference;
import graphical.basics.presentation.RTAnimation;

import java.util.function.Supplier;

public class RTAnimationSlide implements SaturnSlide {
    RTAnimation parent;
    Supplier<RTAnimation> animationSupplier;
    RTAnimation currentAnim;

    public RTAnimationSlide(Supplier<RTAnimation> animationSupplier) {
        this.animationSupplier = animationSupplier;
        parent = (RTAnimation) AnimationStaticReference.staticReference;
    }


    @Override
    public void perform() {
        parent.disable();
        currentAnim = animationSupplier.get();
        new Thread(currentAnim::buildAnimation).start();
    }

    @Override
    public void revert() {
        // nothing its impossible
    }

    @Override
    public void remove() {
        parent.enabled();
        parent.resetContext();
        currentAnim.kill();

    }

    @Override
    public void add() {
        perform();
    }

    @Override
    public void release() {
        remove();
    }
}
