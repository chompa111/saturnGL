package presentation;

import codec.Presentation;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.task.WaitTask;

public class NovoEx extends Presentation {
    @Override
    public void buildPresentation() {

        var circle = CircleBuilder.aCircle().build();

        add(circle);
        execute(new WaitTask(1));

    }

    public static void main(String[] args) {
        new NovoEx().buildPresentation();
    }
}
