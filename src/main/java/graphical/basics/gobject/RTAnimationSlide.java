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
        var parW = parent.getPresentationConfig().getWidth();
        var parH = parent.getPresentationConfig().getHeight();

        currentAnim = animationSupplier.get();
        currentAnim.getAnimationFrame().clearFrame(parW, parH, parent.getBackGround().getColors().get(0).getColor());
        var curW = currentAnim.getPresentationConfig().getWidth();
        var curH = currentAnim.getPresentationConfig().getHeight();


        var ofX = (parW / 2.0) - (curW / 2.0);
        var ofY = (parH / 2.0) - (curH / 2.0);

        RTAnimation.staticReference.getAnimationFrame().setOffset((int) ofX, (int) ofY);
        new Thread(currentAnim::buildAnimation).start();
    }

    @Override
    public void revert() {
        // nothing its impossible
    }

    @Override
    public void remove() {
        currentAnim.returnParentOwnerShip();
        RTAnimation.staticReference.getAnimationFrame().setOffset(0, 0);
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
