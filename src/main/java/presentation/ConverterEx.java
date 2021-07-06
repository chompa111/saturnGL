package presentation;

import codec.Presentation;
import graphical.basics.task.WaitTask;

import java.awt.*;

public class ConverterEx extends Presentation {
    @Override
    public void buildPresentation() {

        Converter cv = new Converter(100);
        cv.readPathSvg("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\src\\main\\resources\\saturn-font-logo.svg");
        // cv.writePolygonSvg(out_filename);

        var polis=cv.polygons();

        polis.forEach(this::add);

        polis.get(0).changeColor(Color.yellow).execute();

        new WaitTask(1).execute();

        cut();
    }

    public static void main(String[] args) {
        new ConverterEx().buildPresentation();
    }
}
