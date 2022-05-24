package codec.engine;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;

public class JavaNativeEngine implements JavaGraphicEngine {

    private final BufferedImage bufferedImage;
    private final Graphics bufferedGraphics;

    public JavaNativeEngine(int width, int height) {
        bufferedImage = new BufferedImage(width, height, TYPE_INT_ARGB);
        bufferedGraphics = bufferedImage.getGraphics();
        final var g2 = (Graphics2D) bufferedGraphics;
        final var rh = new RenderingHints(Map.of(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON));
        g2.setRenderingHints(rh);
    }

    @Override
    public BufferedImage getActualFrame() {
        return bufferedImage;
    }

    @Override
    public Graphics getGraphics() {
        return bufferedGraphics;
    }

    @Override
    public void clear() {
        final var imageWidth = bufferedImage.getWidth();
        final var imageHeight = bufferedImage.getHeight();
        final var pixels = new int[imageWidth * imageHeight];

        Arrays.fill(pixels, 0);
        bufferedImage.setRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
    }
}
