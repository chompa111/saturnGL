package presentation;

import codec.Presentation;
import graphical.basics.gobject.Ball;
import graphical.basics.task.WaitTask;

public class BatikEx extends Presentation {
    @Override
    public void buildPresentation() {

        var batik = new Batiks();
        add(batik);


        new WaitTask(1).execute();

        cut();
    }

    public static void main(String[] args) {
        new BatikEx().buildPresentation();
    }
}
