package graphical.basics.examples;

import codec.engine.EngineType;
import graphical.basics.ColorHolder;
import graphical.basics.behavior.FollowBehavior;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Pencil;
import graphical.basics.gobject.Polygon;
import graphical.basics.location.Location;
import graphical.basics.location.Point;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.value.ChangeType;
import graphical.basics.value.DoubleHolder;

import java.awt.*;

public class Circles extends Presentation {

    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setFramerate(30);
        presentationConfig.setDisableCodec(false);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildPresentation() {
        var c1= CircleBuilder.aCircle().build();
        c1.setRadius(new DoubleHolder(300));
        add(c1);
        c1.setFillColorHolder(new ColorHolder(new Color(0,0,0,0)));
        c1.setStrokeColorHolder(new ColorHolder(Color.yellow));
        c1.setStrokeThickness(new DoubleHolder(2));

        Location pc= Location.getTransformedObservedLocation(Location.at(500+150,500),c1);
        Location p= Location.getTransformedObservedLocation(Location.at(500+150+75,500),c1);

        var c2= CircleBuilder.aCircle().build();
        c2.setRadius(new DoubleHolder(150));
        add(c2);
        c2.setFillColorHolder(new ColorHolder(new Color(0,0,0,0)));
        c2.setStrokeColorHolder(new ColorHolder(Color.green));
        c2.changeSetPosition(150,0);

       var follow= new FollowBehavior(c2,pc);
       add(follow);

       var p2=Location.getTransformedObservedLocation(p,c2);
       add(new Pencil(p2,Color.red));

       c1.getAngle().change(0.05/10,seconds(10), ChangeType.CONSTANT_SPEED).executeInBackGround();
       c2.getAngle().change(0.3/10,seconds(10), ChangeType.CONSTANT_SPEED).executeInBackGround();






    }

    public static void main(String[] args) {
        new Circles().build();
    }
}
