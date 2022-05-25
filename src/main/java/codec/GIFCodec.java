package codec;

import static codec.CodecType.GIF;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GIFCodec implements VideoCodec {

    private int delay;
    private List<BufferedImage> rawImages;
    private String path;

    private static final int UPDATE_RATE = 50;

    @Override
    public void startNewVideo(String path, String name, int frameRate) {
        rawImages = new ArrayList<>();
        this.path = path + name;
        delay = UPDATE_RATE / frameRate;
    }

    @Override
    public void addFrame(final BufferedImage bufferedImage) {
        rawImages.add(deepCopy(bufferedImage));
    }

    @Override
    public void saveVideo() {
        try {
            Giffer.generateFromBI(rawImages.toArray(new BufferedImage[0]), path, delay, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileFormat() {
        return GIF.getFileExtension();
    }

    private static BufferedImage deepCopy(final BufferedImage bi) {
        final var cm = bi.getColorModel();
        final var isAlphaPremultiplied = cm.isAlphaPremultiplied();
        final var raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
