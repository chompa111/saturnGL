package graphical.basics.gobject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Fonts {
    public static Font COMPUTER_MODERN_I;
    public static Font COMPUTER_MODERN_L;

    static {
        try {
           ;
            COMPUTER_MODERN_I = Font.createFont(Font.TRUETYPE_FONT, Paths.get(Fonts.class.getResource("/fonts/cmunbi.ttf").toURI()).toFile());
            COMPUTER_MODERN_L = Font.createFont(Font.TRUETYPE_FONT, Paths.get(Fonts.class.getResource("/fonts/cmunbl.ttf").toURI()).toFile());
           var x= Fonts.class.getResource("/fonts/cmunbi.ttf");
        } catch (FontFormatException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
