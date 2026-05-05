package graphical.basics.listeners;

import graphical.basics.gobject.Group;
import graphical.basics.gobject.shape.ShapeLike;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.gobject.struct.ShapeGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.AnimationFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DragListener {

    private final AnimationFrame frame;
    private final List<Gobject> draggableGobjects = new ArrayList<>();
    private Gobject dragginObject = null;
    private Location refference = null;


    public DragListener(AnimationFrame frame) {
        this.frame = frame;

        frame.getFrame().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                var mousePoint = e.getPoint();


                for (Gobject gobject : draggableGobjects) {
                    if (containsPoint(gobject, mousePoint.x - frame.getOffsetX(), mousePoint.y - frame.getOffsetY())) {
                        dragginObject = gobject;
                        refference = Location.at(mousePoint.x - frame.getOffsetX(), mousePoint.y - frame.getOffsetY());
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                dragginObject = null;
                refference = null;
            }
        });
    }

    public void perform() {
        var point = frame.getFrame().getMousePosition();
        if (point == null) return;
        if (dragginObject != null) {
            var mousePosition = Location.at(point.x - frame.getOffsetX(), point.y - frame.getOffsetY());
            dragginObject.changeSetPosition(mousePosition.getX() - refference.getX(), mousePosition.getY() - refference.getY());
            refference = mousePosition;
        }

    }

    public void add(Gobject gobject) {
        draggableGobjects.add(gobject);
    }

    public static boolean containsPoint(Gobject gobject, double x, double y) {
        if (gobject instanceof ShapeLike) {
            return ((ShapeLike) gobject).asShape().contains(x, y);
        }
        if (gobject instanceof ShapeGobject) {
            return ((ShapeGobject) gobject).getShape().contains(x, y);
        }
        if (gobject instanceof Group) {
            for (var gob : ((Group) gobject).getGobjects()) {
                if (containsPoint(gob, x, y)) {
                    return true;
                }
            }
        }

        if (gobject instanceof SVGGobject) {
            for (var gob : ((SVGGobject) gobject).getShapeGobjects()) {
                if (containsPoint(gob, x, y)) {
                    return true;
                }
            }
        }

        return false;
    }
}
