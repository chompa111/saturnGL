//package graphical.basics.examples;
//
//import com.teamdev.jxbrowser.browser.Browser;
//import com.teamdev.jxbrowser.engine.Engine;
//import com.teamdev.jxbrowser.view.swing.BrowserView;
//
//import javax.swing.*;
//import java.awt.*;
//
//import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
//
//public final class HelloPepe {
//    public static void main(String[] args) {
//        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
//        Browser browser = engine.newBrowser();
//
//        SwingUtilities.invokeLater(() -> {
//            BrowserView view = BrowserView.newInstance(browser);
//
//            JFrame frame = new JFrame("Swing BrowserView");
//            frame.add(view, BorderLayout.CENTER);
//            frame.setSize(700, 500);
//            frame.setVisible(true);
//
//            browser.navigation().loadUrl(
//                    "https://html5test.teamdev.com");
//        });
//    }
//}
