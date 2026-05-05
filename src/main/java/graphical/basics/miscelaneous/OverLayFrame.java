package graphical.basics.miscelaneous;

import javax.swing.*;
import java.awt.*;

class OverlayFrame extends JFrame {
    public OverlayFrame() {
        // Set the frame properties
      //  setUndecorated(true);  // Removes the window decorations
       // setAlwaysOnTop(true); // Ensures the frame is always on top
       // setResizable(false);  // Prevents resizing
       // setBackground(new Color(0, 0, 0, 0));  // Sets transparent background
        setLayout(null); // Allows absolute positioning of components
        setSize(400, 300); // Set the desired size of the frame

        // Create your overlay components and add them to the frame
        // Example:
        JLabel label = new JLabel("Overlay Text");
        label.setForeground(Color.WHITE);
        label.setBounds(50, 50, 300, 200);
        add(label);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        var x =  new JFrame();
        x.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

