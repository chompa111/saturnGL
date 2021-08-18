package graphical.basics.gobject;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Fonts {
    public static Font COMPUTER_MODERN_I;
    public static Font COMPUTER_MODERN_L;

    static {
        try {
            COMPUTER_MODERN_I = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\src\\main\\resources\\cmunbi.ttf"));
            COMPUTER_MODERN_L = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\src\\main\\resources\\cmunbl.ttf"));

        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
