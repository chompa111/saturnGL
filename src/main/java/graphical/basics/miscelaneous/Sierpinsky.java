package graphical.basics.miscelaneous;

import codec.CodecType;
import codec.engine.EngineType;
import graphical.basics.gobject.Rect;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;

public class Sierpinsky extends Animation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

       // presentationConfig.setDisableCodec(true);
        presentationConfig.setFramerate(15);
        presentationConfig.setEngine(EngineType.NATIVE_JAVA);
        presentationConfig.setCodec(CodecType.GIF);
    }

    @Override
    protected void buildAnimation() {
        getBackGround().setColor(Color.white);
        //switchOff();
      //  while (true) {
//            var rect = new Rect(Location.at(333, 333), Location.at(666, 666), Color.white);
//            add(rect);
//            Animation.fadeInGrow(rect, seconds(1)).execute();
//
//            wait(seconds(3)).step(() -> remove(rect)).executeInBackGround();

            sierpinsky(0, 0, 1000, 5, true);
            wait(seconds(6)).execute();

//            getCamera().moveTo(Location.at(6,6),seconds(2))
//                    .andThen(getCamera().getScale().change(80,seconds(2)))
//                    .execute();


            joinBackGroundTasks();
//
//            getCamera().setPositionTo(Location.at(500, 500));
//            getCamera().getScale().setValue(1);

      //  }


        //  switchOn();
    }


    public void sierpinsky(int x, int y, int size, int recursionLimit, boolean color) {
        if (recursionLimit <= 0) {
            return;
        }

        int newsize = size / 3;
        var meio = Location.at(500, 500);
        var p1 = Location.at(x + newsize, y + newsize);
        var p2 = Location.at(x + 2 * newsize, y + 2 * newsize);
        var rect = new Rect(p1,p2 , color ? new Color((int)(p1.distanceTo(meio) % 255), (int)(p2.distanceTo(meio) % 255), 100) : Color.red);

        wait(seconds(6 - recursionLimit)).andThen(() -> {
                    add(rect);
                    return Animations.fadeInGrow(rect, seconds(1));
                }


                //Animation.strokeAndFill(rect,seconds(1))
        )
                //.andThen(rect.getAngle().change(0.05,seconds(10),ChangeType.CONSTANT_SPEED))
                // .andThen(rect.changeColor(Color.red).andThen(rect.changeColor(Color.blue).andThen(rect.changeColor(Color.green).repeat(5))))
//                .andThen(() -> {
//                            var random = Math.random();
//                            return rect.getScale().change(-random, seconds(random + 0.25));
//                                   // .andThen(rect.getScale().change(random, seconds(random + 0.25)))
//                                   // .repeat(4);
//                        }
//                )
                .andThen(wait(seconds(5))
                        .andThen(() -> Animations.fadeOut(rect))
                        .step(() -> remove(rect)))
                .executeInBackGround();

        // if(Math.random()<0.2)wait(1).execute();

        sierpinsky(x, y, newsize, recursionLimit - 1, color);
        sierpinsky(x + newsize, y, newsize, recursionLimit - 1, color);
        sierpinsky(x + 2 * newsize, y, newsize, recursionLimit - 1, color);
        sierpinsky(x + 2 * newsize, y + newsize, newsize, recursionLimit - 1, color);
        sierpinsky(x + 2 * newsize, y + 2 * newsize, newsize, recursionLimit - 1, color);
        sierpinsky(x + newsize, y + 2 * newsize, newsize, recursionLimit - 1, color);
        sierpinsky(x, y + 2 * newsize, newsize, recursionLimit - 1, color);
        sierpinsky(x, y + newsize, newsize, recursionLimit - 1, color);


        //sierpinsky(x + newsize, y + newsize,newsize,recursionLimit-1,!color);


        //  getCamera().moveTo(Location.at(x,y),seconds(1)).execute();

    }


    public static void main(String[] args) {
        new Sierpinsky().build();
    }
}
