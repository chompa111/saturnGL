package graphical.basics.examples;

import codec.CodecType;
import graphical.basics.ColorHolder;
import graphical.basics.Pivot;
import graphical.basics.behavior.FollowBehavior;
import graphical.basics.gobject.*;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Point;
import graphical.basics.location.SupplierPoint;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;
import graphical.basics.task.transformation.value.MeanSpeedTransformation;
import graphical.basics.value.DoubleHolder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExCog extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(true);
        presentationConfig.setFramerate(30);
        presentationConfig.setCodec(CodecType.JCODEC);
    }

    @Override
    public void buildPresentation() {

        List<Gobject> circles = new ArrayList<>();

        int vel = 180_0000;

        double tempo = 3 * 10;

        var r = new DoubleHolder(170);
        var l = new Light(new Point(400, 270), r, new ColorHolder(Color.white));
        add(l);


        Animation.fadeInGrow(l, seconds(2)).execute();

        getBackGround().getColors().get(0).setColor(new Color(0, 0, 0));
        var logo = new SVGGobject("C:\\Users\\PICHAU\\logao.svg");

        var cima = logo.getGroup("cima");
        var baixo = logo.getGroup("baixo");

        cima.getScale().setValue(10);
        baixo.getScale().setValue(10);

        baixo.changeSetPosition(-5, 85);

        var planeta = new Group(cima, baixo);

        planeta.changeSetPosition(200, 200);


         // add(baixo);
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < 7000; i++) {
            var c = CircleBuilder.aCircle().withColor(new Color(255, 255, 255, (int) (Math.random() * 255))).withRadius(3 + Math.random() * 7).build();
//             var c= new SVGGobject("C:\\Users\\PICHAU\\logao.svg").getGroup("planeta");
//             c.getScale().setValue(0.08+Math.random()*0.1);

            add(c);
            int finalI = i;
            tasks.add(new ContextSetupTask(() -> {
                var mid = planeta.getBorders().midPoint();
                var progress = new DoubleHolder(Math.random() * 2 * Math.PI);
                var radius = 130 + Math.random() * 2000;
                var achatamento = 0.2;
                var sup = new SupplierPoint(() -> mid.getX() + (radius * Math.cos(progress.getValue())), () -> mid.getY() + (radius * achatamento * Math.sin(progress.getValue())));
                Pivot pivot = new Pivot(CircleBuilder.aCircle().build(), sup);


                Positioning.align(c, pivot, Positioning.Reference.CENTER);

                var flow = new FollowBehavior(c, sup);
                add(flow);


                return wait(1).andThen(Animation.fadeIn(c, seconds(3))
                        // .parallel(new ConstantSpeedValueTranform(Arrays.asList(c.getAngle()),(0.5-Math.random())*50,seconds(20)))
                        .parallel(c.changeColor(Color.white,15+(int)(Math.random()*16)).andThen((c.changeColor(new Color(0,0,0,0),15+(int)(Math.random()*16)))).repeat(30))
                        .parallel(new MeanSpeedTransformation(Arrays.asList(progress), (vel / (radius * radius)), seconds(tempo))));
            }));

        }

        for (int i = 0; i < 200; i++) {
            Circle c = CircleBuilder.aCircle().withColor(new Color(255, 255, (int) (Math.random() * 255), (int) (Math.random() * 255))).withRadius(5 + Math.random() * 10).build();
            // Circle c=CircleBuilder.aCircle().withColor(ColorHolder.randomColor()).withRadius(5+Math.random()*10).build();
            add(c);
            int finalI = i;
            tasks.add(new ContextSetupTask(() -> {
                var mid = planeta.getBorders().midPoint();
                var progress = new DoubleHolder(Math.random() * 2 * Math.PI);
                var radius = 150.0 + Math.random() * 80;
                var achatamento = 0.2;
                var pwpw = -(Math.random() * 10);
                var sup = new SupplierPoint(() -> mid.getX() + (radius * Math.cos(progress.getValue())), () -> pwpw + mid.getY() + (radius * achatamento * Math.sin(progress.getValue())));
                Pivot pivot = new Pivot(CircleBuilder.aCircle().build(), sup);


                Positioning.align(c, pivot, Positioning.Reference.CENTER);

                var flow = new FollowBehavior(c, sup);
                add(flow);


                var color = c.getColors().get(0).getColor();

                return wait(finalI + 1).andThen(Animation.fadeIn(c, seconds(3)).parallel(new MeanSpeedTransformation(Arrays.asList(progress), (vel / (radius * radius)), seconds(tempo))));
                //.parallel(wait(finalI +1).andThen(c.move(0,-400,seconds(5)).andThen(wait(seconds(5))).andThen(c.move(0,400,seconds(5))).repeat(1)));
            }));

        }

        for (int i = 0; i < 80; i++) {
            Circle c = CircleBuilder.aCircle().withColor(new Color(255, 200, (int) (Math.random() * 255), (int) (Math.random() * 255))).withRadius(5 + Math.random() * 10).build();
            // Circle c=CircleBuilder.aCircle().withColor(ColorHolder.randomColor()).withRadius(5+Math.random()*10).build();
            add(c);
            int finalI = i;
            tasks.add(new ContextSetupTask(() -> {
                var mid = planeta.getBorders().midPoint();
                var progress = new DoubleHolder(Math.random() * 2 * Math.PI);
                var radius = 270.0 + Math.random() * 50;
                var achatamento = 0.2;
                var sup = new SupplierPoint(() -> mid.getX() + (radius * Math.cos(progress.getValue())), () -> mid.getY() + (radius * achatamento * Math.sin(progress.getValue())));
                Pivot pivot = new Pivot(CircleBuilder.aCircle().build(), sup);


                Positioning.align(c, pivot, Positioning.Reference.CENTER);

                var flow = new FollowBehavior(c, sup);
                add(flow);


                circles.add(c);
                return wait(finalI + 1).andThen(Animation.fadeIn(c, seconds(3)).parallel(new MeanSpeedTransformation(Arrays.asList(progress), (vel / (radius * radius)), seconds(tempo))));
            }));

        }


        // add(cima);
        new ParalelTask(tasks)
                .parallel(planeta.move(0, -20).andThen(planeta.move(0, 20)).repeat(2))
                .parallel(
                        l.changeColor(new Color(200, 200, 200), 12)
                                .andThen(l.changeColor(Color.white, 7))
                                .andThen(l.changeColor(new Color(200, 200, 200), 3))
                                .andThen(l.changeColor(Color.white, 5))
                                .andThen(l.changeColor(new Color(200, 200, 200), 13))
                                .andThen(l.changeColor(new Color(220, 220, 220), 3))
                                .andThen(l.changeColor(Color.white, 5))
                                .andThen(l.changeColor(new Color(220, 220, 220), 5))
                                .andThen(l.changeColor(Color.white, 15))
                                .andThen(l.changeColor(new Color(220, 220, 220), 5))
                                .andThen(l.changeColor(Color.white, 8))
                                .repeat(3)
                ).parallel(wait(5).step(() -> {
//                    var qualquer = circles.get(0);
//                   // qualquer.getScale().setValue(10);
//                     Positioning.align(getCamera(), qualquer, Positioning.Reference.CENTER);
//                            add(new FollowBehavior(getCamera(), qualquer.getRefereceLocations().get(0)));

                }
        ))
             //   .parallel(wait(10).andThen(getCamera().getScale().change(1.5,seconds(20))))
                .executeInBackGround();

        wait(seconds(10)).execute();
        cut();
        wait(seconds(10)).execute();
        cut();
        wait(seconds(10)).execute();


//        var engrenagem2 = new SVGGobject("C:\\Users\\PICHAU\\engreno.svg");
//
//        add(engrenagem);
//        add(engrenagem2);
//
//
//
//        engrenagem.changeSetPosition(200, 0);
//        engrenagem2.changeSetPosition(540, 0);
//        engrenagem2.getAngle().setValue(23 * (6.28 / 360));
//
//        Animation.strokeAndFill(engrenagem,seconds(2))
//                .parallel(Animation.fadeInGrow(engrenagem2,seconds(2)))
//                .execute();
//
//        var sub=new LatexGobject(new Font("JetBrains Mono",Font.PLAIN, 40),"Engrenagem 1", new Point(500,500),Color.white);
//        add(sub);
//
//     //   add(behavrior);
//
//
//
//
//        engrenagem.changeColor(new Color(202, 9, 220)).execute();
//
//        engrenagem.rotate(6.28, seconds(4))
//                .parallel(engrenagem2.rotate(-6.28, seconds(4)))
//                .parallel(() -> {
//                    var motor = new Group(engrenagem, engrenagem2);
//                    return motor.move(0, 700, seconds(4));
//                }).andThen(
//
//
//                engrenagem.rotate(-6.28, seconds(4))
//                        .parallel(engrenagem2.rotate(6.28, seconds(4)))
//                        .parallel(() -> {
//                            var motor = new Group(engrenagem, engrenagem2);
//                            return motor.move(0, -700, seconds(4));
//                        })
//        )
//                .repeat(3)
//                .execute();


        new WaitTask(1).execute();
        cut();

    }

    public static void main(String[] args) {
        new ExCog().buildPresentation();
    }
}
