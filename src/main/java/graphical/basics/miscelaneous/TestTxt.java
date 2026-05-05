package graphical.basics.miscelaneous;

import graphical.basics.gobject.ui.TxtBox;
import graphical.basics.location.Location;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;

public class TestTxt extends RTAnimation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    public void buildAnimation() {

        var txtbox = new TxtBox(Location.at(500, 500));
        add(txtbox);

        var txtbox2 = new TxtBox(Location.at(500, 100));
        add(txtbox2);

        addDragBehavior(txtbox);
        addDragBehavior(txtbox2);
    }

    public static void main(String[] args) {
        new TestTxt().buildAnimation();
    }
}
