package codec;

import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GIFCodec implements VideoCodec {

    private int delay;
    private List<BufferedImage> rawImages;
    private String path;

    @Override
    public void startNewVideo(String path, String name, int frameRate) {
        rawImages= new ArrayList<>();
        this.path=path+"/"+name;
        delay=50/frameRate;
    }

    @Override
    public void addFrame(BufferedImage bufferedImage) {
        rawImages.add(deepCopy(bufferedImage));
    }

    @Override
    public void saveVideo() {
        try {
            Giffer.generateFromBI(rawImages.toArray(new BufferedImage[0]) ,path,delay,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileFormat() {
        return ".gif";
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
