package graphical.basics.listeners;

import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class ClickListener {
    private Frame frame;
    private Map<Gobject, Runnable> functions = new HashMap<>();

    public void add(Gobject g, Runnable r) {
        functions.put(g, r);
    }

    public ClickListener(Frame frame) {
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                var mousePoint = e.getPoint();

                for (Gobject gobject : functions.keySet()) {
                    if (gobject instanceof ShapeLike) {
                        if (((ShapeLike) gobject).asShape().contains(mousePoint.x, mousePoint.y)) {
                            functions.get(gobject).run();
                        }
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
            }
        });
    }
}
