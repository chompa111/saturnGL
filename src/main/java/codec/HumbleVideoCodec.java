package codec;

import io.humble.video.*;

import java.awt.image.BufferedImage;

public class HumbleVideoCodec implements VideoCodec {

    MuxerFormat format;
    Codec codec;
    Encoder encoder;

    public HumbleVideoCodec() {
        final Rational framerate = Rational.make(1,60);

        final Muxer muxer = Muxer.make("pepee", null, "mp4");
        final MuxerFormat format = muxer.getFormat();
        final Codec codec;
        codec = Codec.findEncodingCodec(format.getDefaultVideoCodecId());
        Encoder encoder = Encoder.make(codec);
        encoder.setWidth(1000);
        encoder.setHeight(1000);
        final PixelFormat.Type pixelformat = PixelFormat.Type.PIX_FMT_YUV420P;
        encoder.setPixelFormat(pixelformat);
        encoder.setTimeBase(framerate);
        if (format.getFlag(MuxerFormat.Flag.GLOBAL_HEADER))
            encoder.setFlag(Encoder.Flag.FLAG_GLOBAL_HEADER, true);

        /** Open the encoder. */
        encoder.open(null, null);

    }

    @Override
    public void startNewVideo(String path, String name, int frameRate) {
        final Muxer muxer = Muxer.make(name, null, "mp4");
    }

    @Override
    public void addFrame(BufferedImage bufferedImage) {

    }

    @Override
    public void saveVideo() {
    }
}
