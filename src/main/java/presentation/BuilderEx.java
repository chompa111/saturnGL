package presentation;

import codec.Presentation;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.task.WaitTask;

import java.awt.*;

public class BuilderEx extends Presentation {
    @Override
    public void buildPresentation() {
        var circlinho = CircleBuilder.aCircle()
                .withColor(Color.red)
                .build();
        add(circlinho);

        execute(

                circlinho.changeColor(new Color(89,11,160,0))
        );

//        execute(
//                circlinho.getRadius().aceleratedChange(-200)
//                        .parallel(circlinho.changeColor(Color.red))
//        )
//        ;


        execute(new WaitTask(1));
        cut();
    }

    public static void main(String[] args) {
        new BuilderEx().buildPresentation();
    }
}
