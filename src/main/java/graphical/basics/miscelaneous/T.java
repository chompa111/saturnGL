package graphical.basics.miscelaneous;

import javax.swing.*;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class ExternalWindowEmbeddingExample2 extends JPanel {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;

    public ExternalWindowEmbeddingExample2() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Add a component listener to embed the external window when the JPanel is shown
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                // Find the window handle (HWND) of the external window
                WinDef.HWND hwndExternalWindow = findExternalWindowHandle();
                // Embed the external window into the JPanel
                embedExternalWindow(hwndExternalWindow);
            }
        });
    }

    private WinDef.HWND findExternalWindowHandle() {
        // Use JNA to find the window handle (HWND) of the external window
        return User32.INSTANCE.FindWindow(null, "Amigos - Discord"); // Replace "External Window Title" with the actual title of the window you want to embed
    }

    private void embedExternalWindow(WinDef.HWND hwndExternalWindow) {
        // Use JNA to set the parent of the external window to be the JPanel's window
        User32.INSTANCE.SetParent(hwndExternalWindow, new WinDef.HWND(Native.getWindowPointer(SwingUtilities.getWindowAncestor(this))));
        // Resize the external window to fit within the JPanel
        User32.INSTANCE.MoveWindow(hwndExternalWindow, 0, 0, getWidth(), getHeight(), true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("External Window Embedding Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new ExternalWindowEmbeddingExample2());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

