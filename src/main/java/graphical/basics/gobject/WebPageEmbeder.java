package graphical.basics.gobject;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.cert.X509Certificate;

public class WebPageEmbeder {
    JFXPanel fxPanel;
    JFrame frame;
    JButton closeButton;

    public WebPageEmbeder(JFrame frame, String url) {

        System.setProperty("prism.order", "es2,d3d");
        System.setProperty("prism.vsync", "true");
        System.setProperty("prism.verbose", "true");
        this.frame = frame;
        fxPanel = new JFXPanel();
        frame.getContentPane().add(fxPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 0));
        panel.setSize(50, 50);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        //panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        Platform.runLater(() -> {
            // Set system properties for Prism

            WebView webView = new WebView();

            var webEngine = webView.getEngine();
            webEngine.setOnError(event -> System.out.println("Error: " + event));
            webEngine.setOnAlert(event -> System.out.println("Alert: " + event.getData()));

            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    webEngine.executeScript("window.console.log = function(message) { alert(message); };");
                }
            });

            webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");


            webView.getEngine()
                    .load(url);


            Scene scene = new Scene(webView);
            fxPanel.setScene(scene);

        });
//
//        closeButton = new JButton();
//        closeButton.setBorderPainted(false);
//
//        // Remove background
//        closeButton.setBackground(Color.black);
//
//        // Remove focus border
//        closeButton.setFocusPainted(false);
//        closeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                frame.requestFocus();
//            }
//        });
//        frame.getContentPane().add(closeButton, BorderLayout.SOUTH);


    }

    public static JFXPanel secrect(String url) {
        var fxPanel = new JFXPanel();

        Platform.runLater(() -> {
            WebView webView = new WebView();
            webView.setPrefSize(600,400);
            fxPanel.setSize(new Dimension(600,400));
            webView.getEngine().load(url);
            Scene scene = new Scene(webView);
            fxPanel.setScene(scene);
        });

        return fxPanel;
//
//        closeButton = new JButton();
//        closeButton.setBorderPainted(false);
//
//        // Remove background
//        closeButton.setBackground(Color.black);
//
//        // Remove focus border
//        closeButton.setFocusPainted(false);
//        closeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                frame.requestFocus();
//            }
//        });
//        frame.getContentPane().add(closeButton, BorderLayout.SOUTH);


    }

    public void releaseFrame() {
        frame.getContentPane().remove(fxPanel);
        frame.getContentPane().remove(closeButton);
        fxPanel.setScene(null);
    }

    public static void main(String[] args) throws InterruptedException {
        var frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        WebPageEmbeder example = new WebPageEmbeder(frame, "http://www.google.com");
//        example.setVisible(true);

        Thread.sleep(3000);
        // example.releaseFrame();
    }


}
