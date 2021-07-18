package presentation;

import graphical.basics.gobject.Group;
import graphical.basics.gobject.shape.StrokeWriterV2;
import graphical.basics.presentation.Effects;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.gobject.StrokeWriter;
import graphical.basics.gobject.latex.Char;
import graphical.basics.location.Point;
import graphical.basics.task.CodeTask;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.value.DoubleHolder;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Latex extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(60)
                .setDisableCodec(false);
    }

    @Override
    public void buildPresentation() {
        var x = graphical.basics.gobject.latex.lixao.Latex.generateExp("Sanduba", new Point(500, 500), Color.white);
        var s=(Char)x.get(0);
        var group= new Group(x);
        add(group);
        var font = s.getFont();
       // bufferedGraphics.setFont(new Font(font.getFontName(), font.getStyle(), 0));
        var t=new AffineTransform();
        t.translate(520,587);
        t.scale(0.5,0.5);
        t.translate(-520,-587);
        var pepe = s.getFont().createGlyphVector(new FontRenderContext(null,true,true), "Sanduba");

        var pepe2 = new StrokeWriterV2(t.createTransformedShape(pepe.getOutline(500, 500)), Color.white);
        var perc = pepe2.getPerc();

        add(pepe2);

        perc.aceleratedChange(1,seconds(2)).andThen(new ContextSetupTask(()->Effects.fadeIn(group))).execute();
        ;
        cut();
    }

    public static void main(String[] args) {
        new Latex().buildPresentation();
    }
}
