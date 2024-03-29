package graphical.basics.gobject;

import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class Presentation extends RTAnimation {
    public ArrayList<SaturnSlide> slideSequence = new ArrayList<>();
    int index = 0;

    @Override
    public void buildAnimation() {

        addKeyPressedListener(x -> {
            if (x.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (index < slideSequence.size() - 1) {
                    if (index > 0)
                        slideSequence.get(index - 1).remove();
                    slideSequence.get(index).perform();
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

    @Override
    public void setup(PresentationConfig presentationConfig) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        presentationConfig.setWidth(screenSize.width);
        presentationConfig.setHeight(screenSize.height);
        presentationConfig.setPreviewWindowBarVisible(false);

    }

    public void addSlide(SaturnSlide saturnSlide) {
        slideSequence.add(saturnSlide);
    }
}
