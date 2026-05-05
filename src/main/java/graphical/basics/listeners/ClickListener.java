package graphical.basics.listeners;

import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.AnimationFrame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class ClickListener {
    private final AnimationFrame frame;
    private final Map<Gobject, Runnable> functions = new HashMap<>();

    public void add(Gobject g, Runnable r) {
        functions.put(g, r);
    }

    public ClickListener(AnimationFrame animationFrame) {
        this.frame = animationFrame;
        frame.getFrame().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                var mousePoint = e.getPoint();

                for (Gobject gobject : functions.keySet()) {
                    if (DragListener.containsPoint(gobject, mousePoint.x - frame.getOffsetX(), mousePoint.y - frame.getOffsetY())) {
                        functions.get(gobject).run();
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
            }
        });
    }
}
