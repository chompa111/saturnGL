package graphical.basics.listeners;

import graphical.basics.gobject.struct.Gobject;
import graphical.basics.presentation.AnimationFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class FocusListener {

    private Gobject gobjectOnFocus;

    Map<Gobject, Runnable> onFocusFunctions = new HashMap<>();
    Map<Gobject, Runnable> outOfFocusFunctions = new HashMap<>();

    private final AnimationFrame frame;

    public void onFocus(Gobject g, Runnable r) {
        onFocusFunctions.put(g, r);
    }

    public void outOfFocus(Gobject g, Runnable r) {
        outOfFocusFunctions.put(g, r);
    }

    public FocusListener(AnimationFrame animationFrame) {
        this.frame = animationFrame;
        frame.getFrame().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                var mousePoint = e.getPoint();

                boolean match = false;
                for (Gobject gobject : onFocusFunctions.keySet()) {
                    if (DragListener.containsPoint(gobject, mousePoint.x - frame.getOffsetX(), mousePoint.y - frame.getOffsetY())) {
                        if (gobjectOnFocus != gobject) {
                            onFocusFunctions.get(gobject).run();
                            if (outOfFocusFunctions.containsKey(gobjectOnFocus)) {
                                outOfFocusFunctions.get(gobjectOnFocus).run();
                            }
                            gobjectOnFocus = gobject;
                        }
                        match = true;
                    }
                }
                if (!match) {
                    if (outOfFocusFunctions.containsKey(gobjectOnFocus)) {
                        outOfFocusFunctions.get(gobjectOnFocus).run();
                    }
                    gobjectOnFocus = null;
                }

            }

            public void mouseReleased(MouseEvent e) {
            }
        });
    }

    public Gobject getGobjectOnFocus() {
        return gobjectOnFocus;
    }
}
