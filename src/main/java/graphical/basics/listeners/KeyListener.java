package graphical.basics.listeners;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class KeyListener {
    private List<Consumer<KeyEvent>> pressedFunctions = new ArrayList<>();
    private List<Consumer<KeyEvent>> releasedFunctions = new ArrayList<>();

    public KeyListener(Frame frame) {

        frame.setFocusable(true);
        frame.addKeyListener(new java.awt.event.KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                for (int i = 0; i < pressedFunctions.size(); i++) {
                    pressedFunctions.get(i).accept(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                for (int i = 0; i < releasedFunctions.size(); i++) {
                    releasedFunctions.get(i).accept(e);
                }
            }
        });
    }

    public void addPressedFunction(Consumer<KeyEvent> keyEventConsumer){
        pressedFunctions.add(keyEventConsumer);
    }

    public void addReleasedFunction(Consumer<KeyEvent> keyEventConsumer){
        releasedFunctions.add(keyEventConsumer);
    }
}
