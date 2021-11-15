package codec;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import graphical.basics.presentation.PresentationConfig;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class XugglerCodec implements VideoCodec {

    IMediaWriter writer;
    private double fps;
    private boolean framesGenerated;
    private int frameCount = 0;
    PresentationConfig presentationConfig;

    public XugglerCodec(PresentationConfig presentationConfig) {
        this.presentationConfig = presentationConfig;
    }

    @Override
    public void startNewVideo(String path, String name, int frameRate) {
        this.framesGenerated = false;
        try {
            this.writer = ToolFactory.makeWriter(new File(path + "" + name).getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, (int) (presentationConfig.getWidth() * presentationConfig.getScale()), (int) (presentationConfig.getHeight() * presentationConfig.getScale()));
        this.fps = frameRate;
        frameCount = 0;

    }

    @Override
    public void addFrame(BufferedImage bufferedImage) {
        this.framesGenerated = true;
        BufferedImage bgrScreen = convertToType(bufferedImage, 5);
        long nanosecondsElapsed = (long) (1.0E9D * (double) frameCount / this.fps);
        if (frameCount == 60) {
            long var10000 = (long) (1.0E9D * (double) frameCount / this.fps);
        }

        this.writer.encodeVideo(0, bgrScreen, nanosecondsElapsed, TimeUnit.NANOSECONDS);
        frameCount++;
    }

    public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
        BufferedImage image;
        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        } else {
            image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, (ImageObserver) null);
        }

        return image;
    }

    @Override
    public void saveVideo() {
        if (framesGenerated)
            this.writer.close();
    }

    @Override
    public String getFileFormat() {
        return ".mov";
    }


}
