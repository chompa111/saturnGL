package graphical.basics.examples;

import graphical.basics.gobject.CircleBuilder;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.CodeTask;

public class ExCode extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
      //  presentationConfig.setDisableCodec(true);
    }

    @Override
    protected void buildPresentation() {
        var circle = CircleBuilder.aCircle().build();
        add(circle);
        circle.move(300,0).execute();

//        var circle2 = CircleBuilder.aCircle().build();
//        add(circle2);

//        new CodeTask(()->{
//            for(int i=0;i<100;i++){
//                circle.changeSetPosition(2,0);
//                CodeTask.doStep();
//            }
//        }).executeInBackGround();
//
//        new CodeTask(()->{
//            for(int i=0;i<100;i++){
//                circle2.changeSetPosition(4,0);
//                CodeTask.doStep();
//            }
//        }).executeInBackGround();


    }

    public static void main(String[] args) {
        new ExCode().build();
    }
}
