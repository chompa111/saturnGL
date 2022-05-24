package codec;

import static codec.CodecType.JCODEC;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

public class JCodec implements VideoCodec {

    private SeekableByteChannel out;
    private AWTSequenceEncoder encoder;

    @Override
    public void startNewVideo(String path, String name, int frameRate) {
        try {
            out = NIOUtils.writableFileChannel(path + name);
            encoder = new AWTSequenceEncoder(out, Rational.R(frameRate, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFrame(final BufferedImage bufferedImage) {
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

    @Override
    public String getFileFormat() {
        return JCODEC.getFileExtension();
    }
}
