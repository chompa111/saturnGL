package graphical.basics.gobject;

import graphical.basics.examples.regex.Dispatcher;
import graphical.basics.location.Location;
import graphical.basics.presentation.AnimationStaticReference;
import graphical.basics.presentation.RTAnimation;
import graphical.basics.value.DoubleHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;

public class SaturnJComponent {

    public DoubleHolder num;
    public JComponent jComponent;
    public JComponentGobject dadRef;

    JComponent container = new JPanel() {

    };


    public JComponent getContainer(){
        return container;
    }

    public SaturnJComponent(JComponent j) {
        jComponent = j;
        var dim = j.getPreferredSize();
        container.setOpaque(true);
        container.setVisible(true);
        j.setEnabled(false);

        var framex = new JFrame(){
            @Override
            public void paint(Graphics g) {

            }

            @Override
            public void paintComponents(Graphics g) {

            }
        };

        framex.setFocusable(false);

        framex.setSize(dim);
        framex.setVisible(true);
        framex.add(container);
        //framex.setBounds(-1000, -1000, dim.width, dim.height);

        Dispatcher dispatcher = new Dispatcher(framex);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
        Toolkit.getDefaultToolkit().addAWTEventListener(dispatcher, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK| AWTEvent.TEXT_EVENT_MASK| AWTEvent.ITEM_EVENT_MASK);

        num = new DoubleHolder(0);
        container.setBounds(j.getX(), j.getY(), dim.width, dim.height);
        container.add(j, BorderLayout.CENTER);
        RTAnimation.staticReference.getAnimationFrame().addSaturnJComponent(this);
    }

    public void update(Location refference) {
        var dim = jComponent.getPreferredSize();
        container.setBounds((int) refference.getX(), (int) refference.getY(), dim.width, dim.height);
    }

    public Area getArea() {
        var c = container.getBounds();
        return new Area(new Rectangle((int) dadRef.getReferenceLocations().get(0).getX()-1, (int) dadRef.getReferenceLocations().get(0).getY()+30, c.width+10, c.height+30));
    }

    private static void setAllComponentsOpaque(Container container, boolean opaque) {
        for (Component component : container.getComponents()) {
            if (component instanceof Container) {
                setAllComponentsOpaque((Container) component, opaque); // Recursively set components within nested containers
            }
        }
    }

}
