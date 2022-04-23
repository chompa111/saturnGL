package graphical.basics.animations.mandelbrotset;

import codec.CodecType;
import codec.engine.EngineType;
import graphical.basics.gobject.struct.GraphicsGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.Task;
import graphical.basics.value.ChangeType;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class MandelBrotAnim extends Presentation {

    public static final double CENTERX = -1.1044;
    public static final double CENTERY = 0.23345;
    public static int MAXITERATIONS = 200;

    public static final int WIDTH = 1920*2;
    public static final int HEIGHT = 1080*2;

    public static int counter = 0;


    Color color = new Color(0, 0, 20, 0);

    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setWidth(WIDTH);
        presentationConfig.setHeight(HEIGHT);
        presentationConfig.setEngine(EngineType.JAVAFX);
        presentationConfig.setCodec(CodecType.XUGGLE);
        presentationConfig.setScale(0.5);
    }

    @Override
    protected void buildPresentation() {
        //  getBackGround().getBackGroundColor().setColor(new Color(0,0,0,100));
        DoubleHolder zoom = new DoubleHolder(0.001);
        double zum = 0.0008;


        var mandelbrotPlotter = new GraphicsGobject(Location.at(500, 500)) {
            @Override
            public void paint(Graphics g) {


//                for (int x = 300; x < 555; x++) {
//                    g.setColor(Color.getHSBColor((x-300)/255f,1f,1f));
//                            g.drawLine(x, 500, x, 500+300);
//                }


                for (int x = 0; x < WIDTH; x++) {
                    for (int y = 0; y < HEIGHT; y++) {

                        var iteration = iterations(MAXITERATIONS, new ComplexNumber(mapcordX(x, zoom.getValue()), mapcordY(y, zoom.getValue())));
                        if (iteration >= MAXITERATIONS) {
                            g.setColor(color);
                            g.drawLine(x, y, x, y);
                        } else {
//                            var dif=(MAXITERATIONS+0.0f-iteration)/MAXITERATIONS;
//                            if(dif<0.1){
//
//                                g.setColor(Color.getHSBColor((float) (iteration)/ MAXITERATIONS, 0.8f, 1-(dif*10.0f)));
//                            }else{
                            g.setColor(Color.getHSBColor((float) (iteration) / MAXITERATIONS, 0.8f, 1f));
                            //   }
                            g.drawLine(x, y, x, y);
                        }
                    }
                }
            }
        };

        add(mandelbrotPlotter);

        new Task() {
            int count = 0;

            @Override
            public void setup() {

            }

            @Override
            public void step() {
                zoom.setValue(zoom.getValue() * 0.97);
                count++;
            }

            @Override
            public boolean isDone() {
                return count > seconds(15);
            }
        }
                .execute();

    }

    double mapcordX(double v, double zoom) {
        var baseSize = WIDTH * zoom;
        return CENTERX - baseSize + (2 * baseSize * (v / WIDTH));
    }

    double mapcordY(double v, double zoom) {
        var baseSize = HEIGHT * zoom;
        return CENTERY - baseSize + (2 * baseSize * (v / HEIGHT));
    }

    public static void main(String[] args) {
        new MandelBrotAnim().build();
    }


    ComplexNumber function(ComplexNumber z, ComplexNumber c) {
        return ComplexNumber.add(ComplexNumber.mutiply(z, z), c);
    }

    void function2(ComplexNumber z, ComplexNumber c) {
        z.mutiply(z);
        z.add(c);
    }

    int iterations(int maxIterations, ComplexNumber c) {
        ComplexNumber z = new ComplexNumber(0, 0);// zero
        for (int i = 0; i < maxIterations; i++) {
            if (z.magnitude() > 2) {
                return i;
            }
            z=function(z, c);
        }
        return maxIterations;
    }


}
