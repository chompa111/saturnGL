package codec.engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;

public class JavaNativeEngine implements JavaGraphicEngine {

    BufferedImage bufferedImage;
    Graphics bufferedGraphics;

    public JavaNativeEngine(int width, int height) {

        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedGraphics= bufferedImage.getGraphics();
        Graphics2D g2 = (Graphics2D) bufferedGraphics;

        RenderingHints rh = new RenderingHints(

                new HashMap<>() {
                    {
                        put(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                        //put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                       // put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    }
                });

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
        int[] pixels = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];

        Arrays.fill(pixels, 0);
        bufferedImage.setRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth());
    }

}
