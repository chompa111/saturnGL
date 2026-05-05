package graphical.basics.miscelaneous;

import codec.engine.EngineType;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.StringGobject;
import graphical.basics.gobject.Rect;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;
import graphical.basics.task.WaitTask;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class Pong extends RTAnimation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(40);
       // presentationConfig.setScale(0.5);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    public void buildAnimation() {


        long before = System.currentTimeMillis();

        var bar = new Rect(Location.at(0, 200), Location.at(40, 500), Color.white);
        add(bar);

        var bar2 = new Rect(Location.at(0, 200).plus(960,0), Location.at(40, 500).plus(960,0), Color.white);
        add(bar2);
        addDragBehavior(bar2);

        bar2.addBehavior(() -> {
            var newLocation = bar2.getMidPoint();
            newLocation.setX(980);
            bar2.setPositionTo(newLocation);
        });


                new WaitTask(seconds(0.2)).
                andThen(()->bar2.moveTo(Location.at(980,1000*Math.random()),seconds(0.2)))
                .repeat(100_000)
                .executeInBackGround();


        Animations.strokeAndFill(bar, seconds(2)).execute();
        addDragBehavior(bar);
        System.out.println(System.currentTimeMillis() - before+" ms ->27");
        var num = new DoubleHolder(0);
        var counter = new StringGobject("" + num.getValue());
        add(counter);
        counter.setPositionTo(Location.at(500, 50));
        counter.addBehavior(() -> counter.set("" + num.getValue()));
        System.out.println(System.currentTimeMillis() - before+" ms ->33");
        bar.addBehavior(() -> {
            var newLocation = bar.getMidPoint();
            newLocation.setX(20);
            bar.setPositionTo(newLocation);
        });
        System.out.println(System.currentTimeMillis() - before+" ms antes do for");
        for (int i = 0; i < 10; i++) {
            var ball = CircleBuilder.aCircle()
                    .withRadius(20)
                    .withColor(Color.orange)
                    .build();
            add(ball);



            addDragBehavior(ball);

            Animations.strokeAndFill(ball, seconds(1)).step(() -> {
                ball.addBehavior(new Object() {
                    final double velFactor=10;
                    double velx = ((Math.random())-0.5)*velFactor;
                    double vely = ((Math.random())-0.5)*velFactor;
                    boolean ballState = false;
                }, (m) -> {
                    var center = ball.getMidPoint();

                    // tomamo gol
                    if (center.getX() < -100) {
                       ball.setPositionTo(Location.at(500,500));
                        num.setValue(num.getValue() - 1);
                        m.velx = -m.velx;



                        if(m.ballState){
                            ball.changeColor(Color.blue).executeInBackGround();
                        }else{
                            ball.changeColor(Color.yellow).executeInBackGround();
                        }
                        m.ballState = !m.ballState;
                    }

                    // fizemo gol
                    if (center.getX() > 1100) {
                        ball.setPositionTo(Location.at(500,500));
                        num.setValue(num.getValue() + 1);
                        m.velx = -m.velx;



                        if(m.ballState){
                            ball.changeColor(Color.blue).executeInBackGround();
                        }else{
                            ball.changeColor(Color.yellow).executeInBackGround();
                        }
                        m.ballState = !m.ballState;
                    }

                    if (center.getY() < 0) {
                        center.setY(0);
                        m.vely = -m.vely;
                    }
//                    if (center.getX() > 1000) {
//                        center.setX(1000);
//                        m.velx = -m.velx;
//                    }
                    if (center.getY() > 1000) {
                        center.setY(1000);
                        m.vely = -m.vely;


                        if(m.ballState){
                            ball.changeColor(Color.blue).executeInBackGround();
                        }else{
                            ball.changeColor(Color.yellow).executeInBackGround();
                        }
                        m.ballState = !m.ballState;
                    }

                    if (center.getX() < 40 &&
                            center.getY() > bar.getBorders().getL1().getY() &&
                            center.getY() < bar.getBorders().getL2().getY()
                    ) {
                        center.setX(40);
                        m.velx = -m.velx;


                        if(m.ballState){
                            ball.changeColor(Color.blue).executeInBackGround();
                        }else{
                            ball.changeColor(Color.yellow).executeInBackGround();
                        }
                        m.ballState = !m.ballState;

                    }

                    if (center.getX() > 960 &&
                            center.getY() > bar2.getBorders().getL1().getY() &&
                            center.getY() < bar2.getBorders().getL2().getY()
                    ) {
                        center.setX(40);
                        m.velx = -m.velx;


                        if(m.ballState){
                            ball.changeColor(Color.blue).executeInBackGround();
                        }else{
                            ball.changeColor(Color.yellow).executeInBackGround();
                        }
                        m.ballState = !m.ballState;

                    }


                    ball.changeSetPosition(m.velx, m.vely);
                });

            }).executeInBackGround();
            //  new WaitTask(1).execute();

        }



        System.out.println(System.currentTimeMillis() - before+" ms fim");
    }

//    public Pong(RTAnimation rtAnimation) {
//        super(rtAnimation);
//    }

    public static void main(String[] args) {
        new Pong().buildAnimation();
    }
}
