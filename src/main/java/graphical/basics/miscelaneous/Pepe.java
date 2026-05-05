package graphical.basics.miscelaneous;

import graphical.basics.gobject.Video;
import graphical.basics.location.Location;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;

import java.awt.*;

public class Pepe extends RTAnimation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        var x = Toolkit.getDefaultToolkit().getScreenSize();
        presentationConfig.setWidth(2580);
        presentationConfig.setHeight(1080);
        presentationConfig.setPreviewWindowBarVisible(false);
    }

    @Override
    public void buildAnimation() {
        var video = new Video(Location.at(500, 500), "C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv3.mov");
        video.setPositionTo(Location.at(2560.0/2,1080.0/2));
        add(video);
        video.play(seconds(12)).execute();
        var video2 = new Video(Location.at(500, 500), "C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv4.mov");
        add(video2);
        video2.setPositionTo(Location.at(2560.0/2,1080.0/2));
        video2.play(seconds(8)).execute();
        {
            var video3 = new Video(Location.at(500, 500), "C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv5.mov");
            add(video3);
            video3.setPositionTo(Location.at(2560.0/2,1080.0/2));
            video3.play(seconds(8)).execute();
        }
        {
            var video3 = new Video(Location.at(500, 500), "C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv6.mov");
            add(video3);
            video3.setPositionTo(Location.at(2560.0/2,1080.0/2));
            video3.play(seconds(8)).execute();
        }

    }


    public static void main(String[] args) {
        new Pepe().buildAnimation();
    }
}
