package graphical.basics.gobject;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Fonts {
    public static Font COMPUTER_MODERN_I;
    public static Font COMPUTER_MODERN_L;
    public static Font JETBRAINS_MONO;

    static {
            COMPUTER_MODERN_I = pathToFont("/fonts/cmunbi.ttf");
            COMPUTER_MODERN_L = pathToFont("/fonts/cmunbl.ttf");
            JETBRAINS_MONO = pathToFont("/fonts/JetBrainsMono-Regular.ttf");
    }

    private static Font pathToFont(String path) {
        try {
            return Font.createFonts(Objects.requireNonNull(Fonts.class.getResourceAsStream(path)))[0];
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
