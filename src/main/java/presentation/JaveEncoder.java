package presentation;



import codec.VideoCodec;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;

import java.awt.image.BufferedImage;


public class JaveEncoder implements VideoCodec {
    Encoder encoder;

    @Override
    public void startNewVideo(String path, String name, int frameRate) {

    }

    @Override
    public void addFrame(BufferedImage bufferedImage) {

    }

    @Override
    public void saveVideo() {

    }

    public static void main(String[] args) {
        Encoder encoder;
        encoder= new Encoder();
        try {
            encoder.getVideoEncoders();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }
}
