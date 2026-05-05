package graphical.basics.miscelaneous;

import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;

import javax.swing.*;
import java.io.IOException;

public class Testiculo extends JFrame {

    public Testiculo()  {

        try {
            var b = new Browser2("http://www.google.com", false, false, new String[]{});
            add(b.getPanel());
        } catch (UnsupportedPlatformException e) {
            e.printStackTrace();
        } catch (CefInitializationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pack();
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    public static void main(String[] args) {
        new Testiculo();
    }
}
