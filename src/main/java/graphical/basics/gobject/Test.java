package graphical.basics.gobject;

import javafx.embed.swing.JFXPanel;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Test {

    private static void initAndShowGUI() {
        var frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(1500, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var fx = WebPageEmbeder.secrect("http://www.google.com");

        frame.getContentPane().add(fx, BorderLayout.CENTER);
//        example.setVisible(true);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

//    private static void initFX(JFXPanel fxPanel) {
//        // This method is invoked on the JavaFX thread
////        Scene scene = createScene();
//        fxPanel.setScene(scene);
//    }

//    private static Scene createScene() {
//        Group  root  =  new  Group();
//        Scene  scene  =  new  Scene(root, Color.ALICEBLUE);
//        Text  text  =  new  Text();
//
//        text.setX(40);
//        text.setY(100);
//        text.setFont(new Font(25));
//        text.setText("Welcome JavaFX!");
//
//        root.getChildren().add(text);
//
//        return (scene);
//    }

    public static void main(String[] args) throws InterruptedException {

        initAndShowGUI();
        Thread.sleep(3000);
    }
}