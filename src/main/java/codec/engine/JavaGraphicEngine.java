package codec.engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface JavaGraphicEngine {
    BufferedImage getActualFrame();
    Graphics getGraphics();
    void clear();
}
