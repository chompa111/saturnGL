package presentation;

import codec.Presentation;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.StringGobject;
import graphical.basics.location.Point;

import java.awt.*;

public class IgunaaEx extends Presentation {
    @Override
    public void buildPresentation() {
        var circle = CircleBuilder.aCircle().withCenter(200, 200).withColor(new Color(160, 71, 78)).build();
        var circle2 = CircleBuilder.aCircle().withCenter(200 + 100, 200).withColor(Color.blue).build();

        var balls= new Group(circle,circle2);

        var circlinho = CircleBuilder.aCircle().withRadius(400).withColor(Color.red).build();
        //add(circlinho);
        add(circle);
        //add(circle2);


        var string = new StringGobject(new Point(500,500), () -> "(" + (int)circle.getCenter().getX()+","+(int)circle.getCenter().getY()+")", Color.white);
        add(string);

        add(circle.asSubtitle(string, 20));
        string.changeSetPosition(-80,20);
       // circle.getRadius().aceleratedChange(200).execute();
        var gg= new Group(string,circle);

        circle.move(200,0).parallel(circle2.move(-200,0)).parallel(gg.changeColor(Color.orange)).execute();
        circle.move(0,100).execute();
        gg.transform(circlinho).execute();
//
//        var text = Latex.generateExp("iguninhaa", new Point(400, 500+circle.getRadius().getValue()/2), Color.blue);
//        var gg = new GroupGobject(text);
//        add(gg);
//
//        add(new FollowBehavior(gg, new SupplierPoint(circle.getCenter()::getX,()->circle.getCenter().getY()+circle.getRadius().getValue()/2)));
//
//        var taskList = new ArrayList<Task>();
//
////        for (Gobject gobject : text) {
////            taskList.add(
////                    gobject.changeColor(ColorHolder.randomColor())
////                            .parallel(gobject.move((Math.random() * 200) - 100, (Math.random() * 200) - 100))
////            );
////        }
////
////        new ParalelTask(taskList).execute();
//
//      //  gg.transform(circle).execute();
//
//        var rad = circle.getRadius();
//        rad.aceleratedChange(200).parallel(
//                circle.move(200, 0).parallel(circle.changeColor(Color.red))).execute();

        cut();

    }

    public static void main(String[] args) {
        new IgunaaEx().buildPresentation();
    }
}
