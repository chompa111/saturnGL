package codec;

import codec.VideoCodec;
import org.jcodec.api.SequenceEncoder;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class JCodec implements VideoCodec {

    private SeekableByteChannel out;
    public AWTSequenceEncoder encoder;

    @Override
    public void startNewVideo(String path, String name, int frameRate) {
        try {
            out = NIOUtils.writableFileChannel(name);
            encoder = new AWTSequenceEncoder(out, Rational.R(frameRate, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFrame(BufferedImage bufferedImage) {
        try {
            encoder.encodeImage(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveVideo() {
        try {
            encoder.finish();
            NIOUtils.closeQuietly(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
