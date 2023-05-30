package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.AnimationStaticReference;
import graphical.basics.task.SingleStepTask;
import graphical.basics.task.Task;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FrameSequence extends Gobject {
    private double fpsRatio;
    int frameCounter;
    ArrayList<Image> frames = new ArrayList<>();


    public FrameSequence(Location location,String path, int fps) {
        fpsRatio = fps / (AnimationStaticReference.staticReference.seconds(1) + 0.0);
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            frames.add(new Image(location,file));
        }
    }

    @Override
    public void paint(Graphics g) {
        int framePointer = ((int)(frameCounter * fpsRatio))%frames.size();
        frames.get(framePointer).paint(g);
       // frameCounter++;
    }



    @Override
    public LocationPair getBorders() {
        int framePointer = ((int)(frameCounter * fpsRatio))%frames.size();
        return frames.get(framePointer).getBorders();
    }

    @Override
    public List<ColorHolder> getColors() {
        int framePointer = ((int)(frameCounter * fpsRatio))%frames.size();
       return frames.get(framePointer).getColors();
    }

    @Override
    public List<Location> getReferenceLocations() {
        int framePointer = ((int)(frameCounter * fpsRatio))%frames.size();
        return frames.get(framePointer).getReferenceLocations();
    }

    public Task play(int frames){
        return new SingleStepTask(()->{
           frameCounter++;
        }).repeat(frames);
    }

    public Task playUntilTheEnd(){
        int framePointer = ((int)(frameCounter * fpsRatio))%frames.size();
        return play(frames.size()-framePointer);
    }
}
