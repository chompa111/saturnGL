package graphical.basics.gobject;

import graphical.basics.presentation.RTAnimation;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class Presentation extends RTAnimation {
    ArrayList<SaturnSlide> slideSequence = new ArrayList<>();
    int index = 0;


    @Override
    public void buildAnimation() {
        addKeyPressedListener(x -> {
            if (x.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (index < slideSequence.size() - 1) {
                    slideSequence.get(index).perform();
                    if (index > 0)
                        slideSequence.get(index - 1).remove();
                    index++;
                }
            }
        });
        addKeyPressedListener(x -> {
            if (x.getKeyCode() == KeyEvent.VK_LEFT) {
                if (index > 0) {
                    if (index > 1) {
                        slideSequence.get(index - 2).add();
                        slideSequence.get(index - 1).release();
                        index--;
                    }
                }
            }
        });
    }

    public void addSlide(SaturnSlide saturnSlide) {
        slideSequence.add(saturnSlide);
    }
}
