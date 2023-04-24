package graphical.basics.task.transformation.gobject;

import graphical.basics.presentation.Presentation;
import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.GobjectFrame;
import graphical.basics.task.Task;
import graphical.basics.task.TimeDefinedTask;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MorfTransform implements TimeDefinedTask {
    Gobject g1;
    Gobject g2;

    int steps;
    int stepCount;

    List<Pixel> p1;
    List<Pixel> p2;

    GobjectFrame gf1;
    GobjectFrame gf2;

    double[][] aceleration;
    double[][] delta;

    List<ColorHolder> colorHolders1;
    List<ColorHolder> colorHolders2;

    Color color;
    double[][] acelerationcolor;
    double[][] accumulation;
    double[][] deltaColor;

    int refIndex = 0;

    public MorfTransform(Gobject g1, Gobject g2, int steps) {
        this.g1 = g1;
        this.g2 = g2;
        this.steps = steps;
    }

    @Override
    public void setup() {
        // burlando regras
        gf1 = new GobjectFrame(g1);
        gf2 = new GobjectFrame(g2);
        refIndex = Presentation.staticReference.getObjectIndex(g1);
        Presentation.staticReference.remove(g1);
        Presentation.staticReference.add(gf1, refIndex);
        // Presentation.staticReference.add(g2);

        colorHolders1 = new ArrayList<>();
        colorHolders2 = new ArrayList<>();

        p1 = gf1.getPixels();
        p2 = gf2.getPixels();

        // equalizando tamanho das listas

        if (p1.size() < p2.size()) {
            var num = p2.size() - p1.size();
            for (int i = 0; i < num; i++) {
                //p1.add(p1.get((int) (Math.random() * p1.size())).clone());
                //p1.add(p1.get(p1.size()-1).clone());
                var index = (int) (Math.random() * p1.size());
                p1.add(index, p1.get(index).clone());
            }
        }
        if (p2.size() < p1.size()) {
            var num = p1.size() - p2.size();
            for (int i = 0; i < num; i++) {

                var index = (int) (Math.random() * p2.size());
                p2.add(index, p2.get(index).clone());
                //p2.add(p2.get((int) (Math.random() * p2.size())).clone());
                //p2.add(p2.get(p2.size()-1).clone());

            }
        }

        var p1Mid = g1.getBorders().midPoint();
        var p2Mid = g2.getBorders().midPoint();

//        p1.sort(Comparator.comparingDouble(a -> messureAng( p1Mid.getX(), p1Mid.getY(),a.x, a.y)));
//        p2.sort(Comparator.comparingDouble(a -> messureAng( p2Mid.getX(), p2Mid.getY(),a.x, a.y)));


//        p1.sort(Comparator.comparingInt(a -> a.colorHolder.getColor().getRGB()));
//        p2.sort(Comparator.comparingInt(a -> a.colorHolder.getColor().getRGB()));

//        p1.sort(Comparator.comparingDouble(a ->Math.random()));
//        p2.sort(Comparator.comparingDouble(a ->Math.random()));

        aceleration = new double[p1.size()][2];
        delta = new double[p1.size()][2];

        acelerationcolor = new double[p1.size()][4];
        accumulation = new double[p1.size()][4];
        deltaColor = new double[p1.size()][4];

        for (int i = 0; i < p1.size(); i++) {
            colorHolders1.add(p1.get(i).getColorHolder());
            colorHolders2.add(p2.get(i).getColorHolder());

            var amountx = p2.get(i).getX() - p1.get(i).getX();
            var amounty = p2.get(i).getY() - p1.get(i).getY();
            aceleration[i][0] = (4 * amountx) / (2 * steps + (steps * steps));
            aceleration[i][1] = (4 * amounty) / (2 * steps + (steps * steps));


            acelerationcolor[i][0] = 4 * (colorHolders2.get(i).getColor().getRed() - colorHolders1.get(i).getColor().getRed() + 0.0) / (2 * steps + (steps * steps));
            acelerationcolor[i][1] = 4 * (colorHolders2.get(i).getColor().getGreen() - colorHolders1.get(i).getColor().getGreen() + 0.0) / (2 * steps + (steps * steps));
            acelerationcolor[i][2] = 4 * (colorHolders2.get(i).getColor().getBlue() - colorHolders1.get(i).getColor().getBlue() + 0.0) / (2 * steps + (steps * steps));
            acelerationcolor[i][3] = 4 * (colorHolders2.get(i).getColor().getAlpha() - colorHolders1.get(i).getColor().getAlpha() + 0.0) / (2 * steps + (steps * steps));

            accumulation[i][0] = colorHolders1.get(i).getColor().getRed();
            accumulation[i][1] = colorHolders1.get(i).getColor().getGreen();
            accumulation[i][2] = colorHolders1.get(i).getColor().getBlue();
            accumulation[i][3] = colorHolders1.get(i).getColor().getAlpha();

        }


    }

    @Override
    public void step() {

        if (stepCount < (steps / 2)) {
            for (int i = 0; i < p1.size(); i++) {
                delta[i][0] += aceleration[i][0];
                delta[i][1] += aceleration[i][1];
                p1.get(i).setX(p1.get(i).getX() + delta[i][0]);
                p1.get(i).setY(p1.get(i).getY() + delta[i][1]);


                deltaColor[i][0] += acelerationcolor[i][0];
                deltaColor[i][1] += acelerationcolor[i][1];
                deltaColor[i][2] += acelerationcolor[i][2];
                deltaColor[i][3] += acelerationcolor[i][3];

                accumulation[i][0] += deltaColor[i][0];
                accumulation[i][1] += deltaColor[i][1];
                accumulation[i][2] += deltaColor[i][2];
                accumulation[i][3] += deltaColor[i][3];


            }
            //System.out.println("soma:" + stepCount);

        } else {
            for (int i = 0; i < p1.size(); i++) {

                p1.get(i).setX(p1.get(i).getX() + delta[i][0]);
                p1.get(i).setY(p1.get(i).getY() + delta[i][1]);


                deltaColor[i][0] -= acelerationcolor[i][0];
                deltaColor[i][1] -= acelerationcolor[i][1];
                deltaColor[i][2] -= acelerationcolor[i][2];
                deltaColor[i][3] -= acelerationcolor[i][3];

                accumulation[i][0] += deltaColor[i][0];
                accumulation[i][1] += deltaColor[i][1];
                accumulation[i][2] += deltaColor[i][2];
                accumulation[i][3] += deltaColor[i][3];

                delta[i][0] -= aceleration[i][0];
                delta[i][1] -= aceleration[i][1];
            }

            //    System.out.println("sub:" + stepCount);

        }
        for (int i = 0; i < acelerationcolor.length; i++) {
            colorHolders1.get(i).setColor(new Color((int) accumulation[i][0], (int) accumulation[i][1], (int) accumulation[i][2], (int) accumulation[i][3]));
        }

        //acertando
        if (steps == stepCount + 1) {
//            for (int i = 0; i < p1.size(); i++) {
//                p1.get(i).setX(p2.get(i).getX());
//                p1.get(i).setY(p2.get(i).getY());
//            }

            Presentation.staticReference.remove(gf1);
            Presentation.staticReference.add(g2, refIndex);
        }


        stepCount++;

    }

    public static double messureAng(double cx, double cy, double px, double py) {
        double deltay = (py - cy);
        double deltax = ((px - cx) + 0.01);

        double r = Math.atan((py - cy) / ((px - cx) + 0.01));

        if (deltax > 0) {
            if (deltay > 0) {
                return r;
            } else {
                return 2 * 3.1415 + r;
            }

        } else {
            if (deltay > 0) {
                return (3.141592) + r;
            } else {
                return (3.141592) + r;
            }
        }

        // return  Math.atan((py-cy)/((px-cx)+0.01));
    }


    @Override
    public boolean isDone() {
        return steps == stepCount;
    }


    @Override
    public Task forFrames(int frames) {
        this.steps = frames;
        return this;
    }
}
