package codec;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RawImageCodec implements VideoCodec{

    String currName;
    int index=0;
    @Override
    public void startNewVideo(String path, String name, int frameRate) {
        index=0;
        currName="pepe";
    }

    @Override
    public void addFrame(BufferedImage bufferedImage) {
        File outputfile = new File("video/raw/"+currName+""+index+".png");
        index++;
        if(!outputfile.exists()) {
            try {
                outputfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ImageIO.write(bufferedImage, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveVideo() {

    }

    @Override
    public String getFileFormat() {
        return ".png";
    }
}
