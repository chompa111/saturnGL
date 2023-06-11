package graphical.basics.listeners;

import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.AnimationFrame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DragListener {

    private AnimationFrame frame;
    private List<Gobject> draggableGobjects = new ArrayList<>();
    private Gobject dragginObject = null;


    public DragListener(AnimationFrame frame) {
        this.frame = frame;

        frame.getFrame().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                var mousePoint = e.getPoint();


                for (Gobject gobject : draggableGobjects) {
                    if (gobject instanceof ShapeLike) {
                        if (((ShapeLike) gobject).asShape().contains(mousePoint.x - frame.getOffsetX(), mousePoint.y - frame.getOffsetY())) {
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
        var point = frame.getFrame().getMousePosition();
        if (point == null) return;
        if (dragginObject != null)
            dragginObject.setPositionTo(Location.at(point.x- frame.getOffsetX(), point.y-frame.getOffsetY()));
    }

    public void add(Gobject gobject) {
        draggableGobjects.add(gobject);
    }
}
