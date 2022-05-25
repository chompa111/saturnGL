package codec;

public enum CodecType {
    JCODEC(".mov"),
    RAW_IMAGE(".png"),
    XUGGLE(".mov"),
    GIF(".gif");

    private String fileExtension;

    CodecType(final String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
