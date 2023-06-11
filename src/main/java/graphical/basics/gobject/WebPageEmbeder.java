package graphical.basics.gobject;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WebPageEmbeder {
    JFXPanel fxPanel;
    JFrame frame;
    JButton closeButton;

    public WebPageEmbeder(JFrame frame, String url) {
        this.frame = frame;
        fxPanel = new JFXPanel();
        frame.getContentPane().add(fxPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 0));
        panel.setSize(50, 50);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        //panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        Platform.runLater(() -> {
            WebView webView = new WebView();
            webView.getEngine().load(url);
            Scene scene = new Scene(webView);
            fxPanel.setScene(scene);
        });

        closeButton = new JButton();
        closeButton.setBorderPainted(false);

        // Remove background
        closeButton.setBackground(Color.black);

        // Remove focus border
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.requestFocus();
            }
        });
        frame.getContentPane().add(closeButton, BorderLayout.SOUTH);


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
        example.releaseFrame();
    }
}
