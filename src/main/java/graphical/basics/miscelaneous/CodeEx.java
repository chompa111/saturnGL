package graphical.basics.miscelaneous;

import codec.engine.EngineType;
import graphical.basics.behaviors.FollowBehavior;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.CodeBlock;
import graphical.basics.gobject.StringGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animations;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CodeEx extends RTAnimation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(60);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    public void buildAnimation() {
       for(int k=0;k<10;k++) {
            var c = new CodeBlock(Location.at(100, 500), 20, 800);

            var circle = CircleBuilder.aCircle().withCenter(Location.at(200, 200)).build();
            add(circle);
            var txt = new StringGobject("X");
            add(txt);
            circle.addBehavior(FollowBehavior.asSubtitleWithDelay(circle, txt, 20, 0.3));
            txt.addBehavior(() -> txt.set((int) circle.getMidPoint().getX() + "," + (int) circle.getMidPoint().getY()));
            addDragBehavior(circle);

            c.enableInteraction();
            addDragBehavior(c);


            c.newLine("for (int i = 0; i < 5; i++) {");
            c.newLine("    var b =  circle.getCenter().getX();");
            c.newLine("    if(b>200){");
            c.newLine("       circle.changeColor(Color.red).execute();");
            c.newLine("    } else {");
            c.newLine("       circle.changeColor(Color.blue).execute();");
            c.newLine("    } ");
            c.newLine("}");
            c.getTextComment().newLine(" -");
            c.getTextComment().newLine(" -");
            c.getTextComment().newLine(" -");
            c.getTextComment().newLine(" -");
            c.getTextComment().newLine(" -");
            c.addDebugLine();

            add(c);
            Animations.strokeAndFill(c).execute();


            var runner = new CodeRunner(c) {
                @Override
                void code() {
                    for (int i = 0; i < 5; i++) {
                        line(1);
                        evaluate(0, "i:" + i);
                        line(2);
                        var b = circle.getCenter().getX();
                        evaluate(1, "b=" + b);
                        line(3);
                        if (b > 200) {
                            evaluate(2, "" + (b > 200));
                            line(4);
                            circle.changeColor(Color.red).executeInBackGround();
                        } else {
                            evaluate(2, "" + (b > 200));
                            line(6);
                            circle.changeColor(Color.blue).executeInBackGround();
                        }

                    }
                    line(9);
                }

            };


            c.addBehavior(() -> {
                for (int i = 0; i < 5; i++) {
                    if (runner.evaluator.containsKey(i)) {
                        c.getTextComment().replaceLine(i, runner.evaluator.get(i));
                    }
                }
            });

            runner.runCode();

            addKeyPressedListener(e -> {
                if (!isOnFocus(c)) return;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    runner.release();
                }
            });
        }
    }

    public static void main(String[] args) {
        new CodeEx().buildAnimation();
    }


}
