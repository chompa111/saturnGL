package graphical.basics.miscelaneous;
import java.awt.*;
import javax.swing.*;
import com.sun.jna.*;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.*;

class EmbeddedWindowExample extends JFrame {

    public EmbeddedWindowExample() {
        setTitle("Embedded Window Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        // Find the HWND of the window you want to embed
        HWND targetWindow = User32.INSTANCE.FindWindow(null, "Amigos - Discord");

        // Create a Canvas to hold the embedded window
        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                // Get the HDC of the embedded window
                HDC hdc = User32.INSTANCE.GetDC(targetWindow);
                // Paint the content of the embedded window
                GDI32.INSTANCE.BitBlt(g.getClipBounds().x, g.getClipBounds().y, getWidth(), getHeight(),
                        hdc, 0, 0, GDI32.SRCCOPY);
                // Release the HDC
                User32.INSTANCE.ReleaseDC(targetWindow, hdc);
            }
        };

        // Add the canvas to the JFrame
        add(canvas);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmbeddedWindowExample example = new EmbeddedWindowExample();
            example.setVisible(true);
        });
    }

    // User32 and GDI32 interfaces required for native window handling
    public interface GDI32 extends com.sun.jna.platform.win32.GDI32 {
        GDI32 INSTANCE = Native.load("gdi32", GDI32.class);
        boolean BitBlt(int nLeftDest, int nTopDest, int nWidth, int nHeight,
                       HDC hdcSrc, int nLeftSrc, int nTopSrc, int dwRop);
        int SRCCOPY = 0xCC0020;
    }
}
