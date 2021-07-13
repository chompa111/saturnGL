package presentation;

import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.gobject.StrokeWriter;
import graphical.basics.gobject.latex.Char;
import graphical.basics.location.Point;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class Latex extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(60)
                .setDisableCodec(true);
    }

    @Override
    public void buildPresentation() {
        var s = (Char) graphical.basics.gobject.latex.lixao.Latex.generateExp("S", new Point(500, 500), Color.white).get(0);
        add(s);
        var font = s.getFont();
        bufferedGraphics.setFont(new Font(font.getFontName(), font.getStyle(), 5));
        var pepe = s.getFont().createGlyphVector(bufferedGraphics.getFontMetrics().getFontRenderContext(), "bruno");
        var perc = new DoubleHolder(0);
        var pepe2 = new StrokeWriter(pepe.getOutline(500, 500), perc, Color.white);
        add(pepe2);
        s.getColors().get(0).setColor(new Color(0, 0, 0, 0));
        perc.aceleratedChange(1).parallel(s.changeColor(Color.white)).execute();
        ;
        cut();
    }

    public static void main(String[] args) {
        new Latex().buildPresentation();
    }
}
