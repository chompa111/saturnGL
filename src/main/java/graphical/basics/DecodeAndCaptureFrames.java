package graphical.basics;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;

import java.awt.image.BufferedImage;

public class DecodeAndCaptureFrames extends MediaListenerAdapter {
    private int mVideoStreamIndex = -1;
    BufferedImage bu;
    IMediaReader reader;

    boolean pepe = false;


    public DecodeAndCaptureFrames(String videoFile) {
        // create a media reader for processing video
        reader = ToolFactory.makeReader(videoFile);

        // stipulate that we want BufferedImages created in BGR 24bit color space
        reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);


        // note that DecodeAndCaptureFrames is derived from
        // MediaReader.ListenerAdapter and thus may be added as a listener
        // to the MediaReader. DecodeAndCaptureFrames implements
        // onVideoPicture().

        reader.addListener(this);
    }

    public void processFrame() {
        reader.readPacket();
    }

    public BufferedImage getFrame() {
        return bu;
    }


    /**
     * Called after a video frame has been decoded from a media stream.
     * Optionally a BufferedImage version of the frame may be passed
     * if the calling {@link IMediaReader} instance was configured to
     * create BufferedImages.
     * <p>
     * This method blocks, so return quickly.
     */

    public void onVideoPicture(IVideoPictureEvent event) {
        // if the stream index does not match the selected stream index,
        // then have a closer look

        if (event.getStreamIndex() != mVideoStreamIndex) {
            // if the selected video stream id is not yet set, go ahead an
            // select this lucky video stream

            if (-1 == mVideoStreamIndex)
                mVideoStreamIndex = event.getStreamIndex();

                // otherwise return, no need to show frames from this video stream

            else {
                pepe = false;
                return;

            }


        }
        bu = event.getImage();
        pepe = false;
    }
}