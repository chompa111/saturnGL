package graphical.basics.miscelaneous;

import graphical.basics.ColorHolder;
import graphical.basics.behaviors.FollowBehavior;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.StringGobject;
import graphical.basics.gobject.Rect;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;
import graphical.basics.value.ChangeType;

import java.awt.*;

public class RTEx extends RTAnimation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
    }

    @Override
    public void buildAnimation() {
        


        for (int i = 0; i < 10; i++) {
            var circle = CircleBuilder.aCircle()
                    .withCenter(Math.random() * 1000, Math.random() * 1000)
                    .withColor(ColorHolder.randomColor())
                    .withRadius(20)
                    .build();
            add(circle);

            Animations.strokeAndFill(circle).executeInBackGround();

            var txt = new StringGobject("X");
            add(txt);

            

            circle.addBehavior(FollowBehavior.asSubtitleWithDelay(circle, txt, 20, 0.3));

            txt.addBehavior(() -> txt.set((int) circle.getMidPoint().getX() + "," + (int) circle.getMidPoint().getY()));

            addDragBehavior(circle);
        }

        var square = new Rect(Location.at(500, 500), Location.at(600, 600), Color.orange);
        add(square);
        Animations.strokeAndFill(square).execute();
       addDragBehavior(square);
        square.getAngle().change(0.1, seconds(30), ChangeType.CONSTANT_SPEED).executeInBackGround();
        //square.getScale().change(0.2,seconds(30), ChangeType.CONSTANT_SPEED).execute();


        var circle = CircleBuilder.aCircle()
                .withCenter(300, 500)
                .withColor(Color.orange)
                .withRadius(15)
                .build();
        add(circle);

        circle.addBehavior(() -> {
            circle.getCenter().setY(((int) (circle.getCenter().getY() / 30)) * 30);
            if (circle.getCenter().getY() > 600) {
                circle.getCenter().setY(600);
            }
            if (circle.getCenter().getY() < 400) {
                circle.getCenter().setY(400);
            }

            circle.getCenter().setX(300);
        });

        addClickListener(square,()->{
            square.changeColor(ColorHolder.randomColor()).executeInBackGround();
        });

        circle.addBehavior(() -> square.getScale().setValue(1+(circle.getCenter().getY()-400) / 100.0));

        var txt = new StringGobject("X");
        add(txt);

        circle.addBehavior(FollowBehavior.asSubtitleWithDelay(circle, txt, 20, 0.3));
        txt.addBehavior(() -> txt.set((int) circle.getMidPoint().getX() + "," + (int) circle.getMidPoint().getY()));

        addDragBehavior(circle);

        Animations.strokeAndFill(circle).executeInBackGround();


        addBehavior(new Object() {
            double lastState = 0.0;
        }, (x) -> {
            if (x.lastState != circle.getCenter().getY()) {
                x.lastState = circle.getCenter().getY();
                circle.changeColor(Color.WHITE).forSeconds(0.2)
                        .andThen(circle.changeColor(Color.orange).forSeconds(0.2))

                        .parallel(circle.getScale().change(-0.1).forSeconds(0.2)
                                .andThen(circle.getScale().change(0.1).forSeconds(0.2)))
                        .executeInBackGround();
            }
        });

        Object lock = new Object();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                breakpoint(lock);
                System.out.println("released");
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println("l");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                release(lock);
            }
        }).start();

    }

    void breakpoint(Object lock) {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void release(Object lock) {
        synchronized (lock) {
            lock.notify();
        }
    }

//    public RTEx(RTAnimation rtAnimation) {
//        super(rtAnimation);
//    }

        public static void main(String[] args) {
        new RTEx().buildAnimation();
    }
}
