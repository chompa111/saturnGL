package graphical.basics.presentation;

import codec.CodecType;

public class PresentationConfig {

    public PresentationConfig() {
    }

    private int width = 1000;//default value
    private int height = 1000;// defaultvalue;

    private boolean previewWindowBarVisible = false;

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isPreviewWindowBarVisible() {
        return previewWindowBarVisible;
    }

    public void setPreviewWindowBarVisible(boolean previewWindowBarr) {
        this.previewWindowBarVisible = previewWindowBarr;
    }
}
