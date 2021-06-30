package codec;

import java.awt.image.BufferedImage;

public interface VideoCodec {

    void startNewVideo(String path, String name, int frameRate);

    void addFrame(BufferedImage bufferedImage);

    void saveVideo();
}
