package presentation;

import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.latex.lixao.Latex;
import graphical.basics.location.Point;

import java.awt.*;

public class ExBardeli extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {

    }

    @Override
    public void buildPresentation() {
        var cicle = CircleBuilder.aCircle()
                .build();

        add(cicle);

        var radius = cicle.getRadius();


        radius.aceleratedChange(200)
                .parallel(cicle.move(200, 0, seconds(0.5))
                        .parallel(cicle.changeColor(Color.red)))
                .execute();
       var text= Latex.generateExp("igunaa",new Point(500,500),Color.magenta);
        var gg = new Group(text);

        cicle.transform(gg).execute();

        cut();

    }

    public static void main(String[] args) {
        new ExBardeli().buildPresentation();
    }
}
