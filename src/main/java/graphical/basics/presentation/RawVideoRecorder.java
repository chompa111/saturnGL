package graphical.basics.presentation;

import codec.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Consumer;

public class RawVideoRecorder {
    final private PresentationConfig presentationConfig;

    private VideoCodec videoCodec;

    public RawVideoRecorder(Consumer<PresentationConfig> configurer) {
        this.presentationConfig = new PresentationConfig();
        configurer.accept(presentationConfig);
        applyConfig();
        start();
    }

    public RawVideoRecorder(PresentationConfig presentationConfig) {
        this.presentationConfig = presentationConfig;
        applyConfig();
        start();
    }

    private void applyConfig() {
        if (presentationConfig.getCodec() != null) {
            switch (presentationConfig.getCodec()) {
                case JCODEC:
                    this.videoCodec = new JCodec();
                    break;
                case RAW_IMAGE:
                    this.videoCodec = new RawImageCodec();
                    break;
                case XUGGLE:
                    this.videoCodec = new XugglerCodec(presentationConfig);
                    break;
                case GIF:
                    this.videoCodec = new GIFCodec();
            }

        } else {
            this.videoCodec = new XugglerCodec(presentationConfig);//default
        }
    }

    private void start() {
        var executionPath = System.getProperty("user.dir");
        File videoDir = new File(executionPath + "/video");
        videoDir.mkdir();

        File rawDir = new File(executionPath + "/video/raw");
        rawDir.mkdir();

        videoCodec.startNewVideo(executionPath + "/video/", "mv" + videoCodec.getFileFormat(), presentationConfig.getFramerate());
    }

    public void addFrame(BufferedImage bufI) {
        videoCodec.addFrame(bufI);
    }

    public void saveVideo() {
        videoCodec.saveVideo();
    }

}
