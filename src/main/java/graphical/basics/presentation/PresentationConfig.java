package graphical.basics.presentation;

public class PresentationConfig {

    public PresentationConfig() {
    }

    private Boolean disableCodec;
    private Integer framerate;

    public Boolean isDisableCodec() {
        return disableCodec;
    }

    public PresentationConfig setDisableCodec(boolean disableCodec) {
        this.disableCodec = disableCodec;
        return this;
    }

    public Integer getFramerate() {
        return framerate;
    }

    public PresentationConfig setFramerate(int framerate) {
        this.framerate = framerate;
        return this;
    }
}
