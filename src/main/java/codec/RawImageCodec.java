package codec;

import static codec.CodecType.RAW_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RawImageCodec implements VideoCodec {

    private static final String BASE_PATH = "video/raw/";
    private static final String FORMAT_NAME = "png";
    private String fileName;
    private int index;

    @Override
    public void startNewVideo(String path, String name, int frameRate) {
        index = 0;
        fileName = name;
    }

    @Override
    public void addFrame(BufferedImage bufferedImage) {
        final var outputFile = new File(getOutputPath());
        index++;
        createFile(outputFile);
        writeImageToFile(bufferedImage, outputFile);
    }

    private void writeImageToFile(final BufferedImage bufferedImage, final File outputFile) {
        try {
            ImageIO.write(bufferedImage, FORMAT_NAME, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile(final File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getOutputPath() {
        return BASE_PATH + fileName + index + getFileFormat();
    }

    @Override
    public void saveVideo() {
    }

    @Override
    public String getFileFormat() {
        return RAW_IMAGE.getFileExtension();
    }
}
