package graphical.basics.miscelaneous;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.*;
import graphical.basics.gobject.Rect;
import graphical.basics.gobject.struct.ClipArea;
import graphical.basics.location.Location;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;
import graphical.basics.value.DoubleHolder;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RandomName extends RTAnimation {


    public static void main(String[] args) {
        new RandomName().buildAnimation();
    }

    @Override
    public void setup(PresentationConfig presentationConfig) {
//        presentationConfig.setEngine(EngineType.JAVAFX);
        presentationConfig.setFramerate(40);
    }

    @Override
    public void buildAnimation() {

        var rr = new RoundRect(Location.at(200,500),new DoubleHolder(500),new DoubleHolder(50),Color.black);
        rr.setStrokeColorHolder(new ColorHolder(Color.white));
        rr.getRoundness().setValue(0.35);
        add(rr);
        addDragBehavior(rr);
        rr.setStrokeThickness(new DoubleHolder(2));

        var  clip=new ClipArea(rr);

       var y = new Object(){

       };


//        for(int i =0;i<400;i++){
//            var c = CircleBuilder.aCircle().withCenter(Math.random()*1000,Math.random()*1000)
//                    .withColor(ColorHolder.randomColor())
//                    .build();
//
//            var x=Math.random()*300;
//            var y=Math.random()*300;
//            c.move(x,y).andThen(c.move(-x,-y)).repeat(100).executeInBackGround();
//            new WaitTask(1).execute();
//
//            add(c);
//
//        }



        var cursor = new Rect(Location.at(490, 480), Location.at(495, 540), new Color(10, 60, 140));
        cursor.changeColor(new Color(255, 255, 255,0), 10).andThen(cursor.changeColor(new Color(255, 255, 255), 10))
                .repeat(100).repeat(100)
                .executeInBackGround();

        cursor.changeSetPosition(30, -20);

        var txt = new Text(Color.white, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(500, 500));
        txt.newLine("*");
        onFocus(txt, () -> clip.add(cursor));
        outOfFocus(txt, () -> clip.remove(cursor));

        var hl = new JavaHilighter();
        txt.addBehavior(() -> {
            hl.colorize(txt);
            //   txt.setPositionTo(Location.at(500, 500));

        });

        cursor.addBehavior(() -> {
            var l = txt.getLine(0).getBorders();
            if (l == null) return;
            cursor.setPositionTo(Location.at(l.getL2().getX(), cursor.getMidPoint().getY()).plus(10, 0));
        });

        //add(txt);


        addDragBehavior(txt);
        clip.add(txt);




        addKeyPressedListener(e -> {
            if (!isOnFocus(txt)) return;
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                var string = txt.getLine(0).getString();
                var newString = string.substring(0, string.length() - 1);
                txt.getLine(0).set(newString);
            } else {
                char keyChar = e.getKeyChar();
                // if (Character.isLetter(keyChar)) {
                String keyText = Character.toString(keyChar);
                txt.getLine(0).set(txt.getLine(0).getString() + keyText);
            }

        });
    }
}
