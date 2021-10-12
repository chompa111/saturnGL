package graphical.basics.examples;

import codec.engine.EngineType;
import graphical.basics.gobject.FrameSequence;
import graphical.basics.gobject.Video;
import graphical.basics.gobject.struct.SVGGobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.value.ChangeType;

public class HomemPalito extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setDisableCodec(false);
        presentationConfig.setEngine(EngineType.JAVAFX);
    }

    @Override
    protected void buildPresentation() {

//        var video = new Video(Location.at(300, 300), "C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\my_videos\\banana.mp4");
//        add(video);
//        video.getScale().setValue(0.3);

     //   video.play(seconds(20)).executeInBackGround();

        var fs = new FrameSequence(Location.at(500, 500), "C:\\Users\\PICHAU\\Pictures\\satAnim", 10);
        add(fs);

        fs.play(seconds(10)).execute();


    }

    public static void main(String[] args) {
        new HomemPalito().build();
    }
}
