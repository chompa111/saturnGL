package codec;

import static codec.CodecType.XUGGLE;
import static com.xuggle.xuggler.ICodec.ID.CODEC_ID_H264;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import graphical.basics.presentation.PresentationConfig;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class XugglerCodec implements VideoCodec {

    private static final double NANOSECOND = 1.0E9D;
    private final PresentationConfig presentationConfig;
    private IMediaWriter writer;
    private double fps;
    private boolean framesGenerated;
    private int frameCount;


    public XugglerCodec(PresentationConfig presentationConfig) {
        this.presentationConfig = presentationConfig;
    }

    @Override
    public void startNewVideo(String path, String name, int frameRate) {
        framesGenerated = false;
        initWriter(path, name);
        setupVideoResolution();
        fps = frameRate;
        frameCount = 0;
    }

    private void setupVideoResolution() {
        final var presentationScale = presentationConfig.getScale();
        final var videoWidth = (int) (presentationConfig.getWidth() * presentationScale);
        final var videoHeight = (int) (presentationConfig.getHeight() * presentationScale);
        writer.addVideoStream(0, 0, CODEC_ID_H264, videoWidth, videoHeight);
    }

    private void initWriter(String path, String name) {
        try {
            writer = ToolFactory.makeWriter(new File(path + name).getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFrame(BufferedImage bufferedImage) {
        framesGenerated = true;
        final var bgrScreen = convertToType(bufferedImage, TYPE_3BYTE_BGR);
        final var nanosecondsElapsed = (long) (NANOSECOND * frameCount / fps);
        writer.encodeVideo(0, bgrScreen, nanosecondsElapsed, TimeUnit.NANOSECONDS);
        frameCount++;
    }

    public static BufferedImage convertToType(final BufferedImage sourceImage, int targetType) {
        if (sourceImage.getType() == targetType)
            return sourceImage;

        final var image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
        image.getGraphics().drawImage(sourceImage, 0, 0, null);
        return image;
    }

    @Override
    public void saveVideo() {
        if (framesGenerated)
            writer.close();
    }

    @Override
    public String getFileFormat() {
        return XUGGLE.getFileExtension();
    }
}
