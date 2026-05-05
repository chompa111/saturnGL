package graphical.basics.gobject.ui;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.*;
import graphical.basics.gobject.Rect;
import graphical.basics.location.Location;
import graphical.basics.presentation.RTAnimation;
import graphical.basics.value.DoubleHolder;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TxtBox extends Group {

    private Text txtBody;
    private Rect cursor;
    private RoundRect background;

    private int line = 0;
    private int column = 0;

    public TxtBox(Location location) {
        this.txtBody = new Text(Color.white, Fonts.JETBRAINS_MONO.deriveFont(30f), location);
        txtBody.newLine("sdj");
        background = new RoundRect(location.plus(-10,-40), new DoubleHolder(200), new DoubleHolder(50), Color.black);
        background.setStrokeColorHolder(new ColorHolder(new Color(180, 180, 180)));
        background.getRoundness().setValue(0.3);
        background.setStrokeThickness(new DoubleHolder(2.2));
        cursor = new Rect(Location.at(490, 480), Location.at(495, 540), new Color(10, 60, 140));

        cursor.changeColor(new Color(255, 255, 255,0), 10).andThen(cursor.changeColor(new Color(255, 255, 255), 10))
                .repeat(100).repeat(100)
                .executeInBackGround();

        add(background);
        //add(cursor);
        add(txtBody);


        RTAnimation.staticReference.addKeyPressedListener(e -> {
            if (!RTAnimation.staticReference.isOnFocus(this)) return;
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                var string = txtBody.getLine(0).getString();
                var newString = string.substring(0, string.length() - 1);
                txtBody.getLine(0).set(newString);
            } else {
                char keyChar = e.getKeyChar();
                // if (Character.isLetter(keyChar)) {
                String keyText = Character.toString(keyChar);
                txtBody.getLine(0).set(txtBody.getLine(0).getString() + keyText);
            }
        });

        onFocus(() -> add(cursor));
        outOfFocus(() -> remove(cursor));

        cursor.addBehavior(()->{
            var l = txtBody.getLine(0).getBorders();
            if (l == null) return;
            cursor.setPositionTo(Location.at(l.getL2().getX(), cursor.getMidPoint().getY()).plus(10, 0));
        });
        background.addBehavior(()->{
            var l = txtBody.getLine(0).getBorders();
            if (l == null) return;
            var txtw=l.getwidth();
            background.getWidthValue().setValue(txtw+20);
        });


    }


}
