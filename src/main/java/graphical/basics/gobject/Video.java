package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.DecodeAndCaptureFrames;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.presentation.AnimationStaticReference;
import graphical.basics.task.SingleStepTask;
import graphical.basics.task.Task;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Video extends Gobject {

    Location location;

    DecodeAndCaptureFrames decodeAndCaptureFrames;

    int width;
    int heith;

    public Video(Location location, String path) {
        this.location = location;
        decodeAndCaptureFrames = new DecodeAndCaptureFrames(path);
        var img = decodeAndCaptureFrames.getFrame();
        width = img.getWidth();
        heith = img.getHeight();
    }

    @Override
    public void paint(Graphics g) {
        var img = decodeAndCaptureFrames.getFrame();
        g.drawImage(img, (int) location.getX() - (width / 2), (int) location.getY() - (heith / 2), null);
    }

    @Override
    public LocationPair getBorders() {
        return new LocationPair(Location.at(location.getX() - (width * scale.getValue()), location.getY() - (heith * scale.getValue())), Location.at(location.getX() + (width * scale.getValue()), location.getY() + (heith * scale.getValue())));
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList();
    }

    @Override
    public List<Location> getReferenceLocations() {
        return Arrays.asList(location);
    }

    public Task play(int frames) {
        return new SingleStepTask(() -> {
            decodeAndCaptureFrames.processFrame();
        }).repeat(frames);
    }

    static public String getValidJarPath(String resourceName) {
        try {
            var inputStream = AnimationStaticReference.staticReference.getClass().getResourceAsStream(resourceName);
            var tempFile = File.createTempFile("temp" + (int) (Math.random() * 100000), ".mp4");
            tempFile.deleteOnExit();

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
            return tempFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void startOver() {
        decodeAndCaptureFrames.config();
    }
}
