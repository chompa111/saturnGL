package codec;

import java.awt.image.BufferedImage;
import java.security.cert.Extension;

public interface VideoCodec {

    void startNewVideo(String path, String name, int frameRate);

    void addFrame(BufferedImage bufferedImage);

    void saveVideo();

     String getFileFormat();
}
