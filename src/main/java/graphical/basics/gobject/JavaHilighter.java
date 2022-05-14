package graphical.basics.gobject;

import java.awt.*;
import java.util.Set;

public class JavaHilighter {

    public static final Color INTELLIJ_ORANGE = new Color(194, 113, 50);
    public static final Color INTELLIJ_LIGHT_BLUE = new Color(91, 142, 184);
    public static final Color INTELLIJ_GRAY = new Color(161, 193, 193);
    public static final Color INTELLIJ_PURPLE = new Color(142, 110, 128);
    public static final Color INTELLIJ_DARK_GRAY = new Color(86, 111, 127);
    public static final Color INTELLIJ_LIGHT_YELLOW = new Color(211, 229, 73);
    public static final Color INTELLIJ_BACKGROUND = new Color(43, 43, 44);
    public static final Color INTELLIJ_DEBUGGER_COLOR = new Color(45, 95, 153,140);
    public static final Color GUTTER_COLOR = new Color(49, 51, 54);
    //asdhjkashdkjashdkjashjk


    public static Set<String> RESERVED_WORDS =
            Set.of("public", "static", "if", "class", "void",
                    "final", "var", "int", "long", "double",
                    "for", "while", "do", "return", "import","new");


    public void colorize(Text text) {
        for (StringGobject s : text.getLines()) {

            if (s.getString().contains("//")) {
                s.setColor(INTELLIJ_DARK_GRAY);
            }

            for (String term : s.getString().split("[ (){}<>+\\-=;]")) {
                term = term.replaceAll("[^a-zA-Z0-9]*", "");
                if (RESERVED_WORDS.contains(term)) {
                    s.getFirstSubstring(term).setColor(INTELLIJ_ORANGE);
                } else {
                    if (s.getString().matches("((void|int|double|float|boolean)|[A-Z][a-z0-9]*)\\s"+term+"\\s*\\(.*")){
                        s.getFirstSubstring(term).setColor(INTELLIJ_LIGHT_YELLOW);
                    }
                }
                if (term.matches("[0-9]+[dDfF]?")) {
                    s.getFirstSubstring(term).setColor(INTELLIJ_LIGHT_BLUE);
                }
            }
        }
    }
}
