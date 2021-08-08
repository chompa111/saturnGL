package graphical.basics.presentation;

import codec.CodecType;

public class PresentationConfig {

    public PresentationConfig() {
    }

    private CodecType codecType;
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

    public CodecType getCodec() {
        return codecType;
    }

    public PresentationConfig setCodec(CodecType codecType) {
        this.codecType = codecType;
        return this;
    }
}
