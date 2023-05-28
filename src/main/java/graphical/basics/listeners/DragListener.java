package graphical.basics.listeners;

import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DragListener {

    private Frame frame;
    private List<Gobject> draggableGobjects = new ArrayList<>();
    private Gobject dragginObject = null;


    public DragListener(Frame frame) {
        this.frame = frame;

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                var mousePoint = e.getPoint();

                for (Gobject gobject : draggableGobjects) {
                    if (gobject instanceof ShapeLike) {
                        if (((ShapeLike) gobject).asShape().contains(mousePoint.x, mousePoint.y)) {
                            dragginObject = gobject;
                        }
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                dragginObject = null;
            }
        });
    }

    public void perform() {
        var x = frame.getMousePosition();
        if (x == null) return;
        if (dragginObject != null)
            dragginObject.setPositionTo(Location.at(x.x, x.y));
    }

    public void add(Gobject gobject) {
        draggableGobjects.add(gobject);
    }
}
