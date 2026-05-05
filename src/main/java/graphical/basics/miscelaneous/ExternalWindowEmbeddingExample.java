package graphical.basics.miscelaneous;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ExternalWindowEmbeddingExample extends Panel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public ExternalWindowEmbeddingExample() {

        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setBackground(Color.red);

        // Add a component listener to embed the external window when the JFrame is shown
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                // Find the window handle (HWND) of the external window
                WinDef.HWND hwndExternalWindow = findExternalWindowHandle();
                // Embed the external window into the Java application
                embedExternalWindow(hwndExternalWindow);
            }
        });

        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Find the window handle (HWND) of the external window
            WinDef.HWND hwndExternalWindow = findExternalWindowHandle();
            // Embed the external window into the Java application
            embedExternalWindow(hwndExternalWindow);
        }).start();
    }

    private WinDef.HWND findExternalWindowHandle() {

        // Use JNA to find the window handle (HWND) of the external window
        return User32.INSTANCE.FindWindow(null, "Amigos - Discord"); // Replace "External Window Title" with the actual title of the window you want to embed
    }

    private void embedExternalWindow(WinDef.HWND hwndExternalWindow) {
        WinDef.HWND hwnd = new WinDef.HWND();
        hwnd.setPointer(Native.getComponentPointer(this));
        // Use JNA to set the parent of the external window to be the Java application's window
        User32.INSTANCE.SetParent(hwndExternalWindow,hwnd);
        // Resize the external window to fit within the Java application's window
        User32.INSTANCE.MoveWindow(hwndExternalWindow, 0, 0, getWidth(), getHeight(), true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExternalWindowEmbeddingExample example = new ExternalWindowEmbeddingExample();
            example.setVisible(true);
        });
    }
}
