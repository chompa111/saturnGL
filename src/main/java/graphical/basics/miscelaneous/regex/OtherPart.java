package graphical.basics.miscelaneous.regex;

import graphical.basics.gobject.AnimPart;
import graphical.basics.gobject.StringGobject;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;

import java.awt.*;

public class OtherPart extends AnimPart {
    @Override
    protected void buildPresentation(Gobject... gobjects) {
        var x = new StringGobject("regex");
        x.setColor(Color.white);
        x.setPositionTo(Location.at(500,500));
        add(x);

        var egex=x.getFirstSubstring("egex");

        egex.move(20,0,seconds(0.5)).execute();
    }
}
