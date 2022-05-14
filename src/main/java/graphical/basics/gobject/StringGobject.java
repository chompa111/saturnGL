package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.struct.ShapeGobject2;
import graphical.basics.location.Location;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.SequenceTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StringGobject extends Group {

    private String string;
    private ArrayList<Integer> spacemapping;
    private final Location ref;


    public StringGobject(String string) {
        this(string, Fonts.JETBRAINS_MONO.deriveFont(30f), Location.at(0, 0), Color.white);
    }


    public StringGobject(String string, Font font, Location location, Color color) {
        this.string = string;
        this.spacemapping = extractSpaceMapping(string);
        this.ref = location;

        addAll(generateText(font, string, location, color));
    }


    public static java.util.List<Gobject> generateText(Font font, String s, Location location, Color color) {

        List<Gobject> gobjects = new ArrayList<>();
        var gv = font.createGlyphVector(new FontRenderContext(null, true, true), s);

        for (int i = 0; i < s.length(); i++) {
            var c = s.charAt(i);
            if (c == ' ') continue;
            var sh = gv.getGlyphOutline(i);
            var t = new AffineTransform();
            t.translate(location.getX(), location.getY());
            sh = t.createTransformedShape(sh);
            //gobjects.add(new Char2(font, new Point(sh.getBounds().x , sh.getBounds().y ), new char[]{c}, font.getSize(), color));
            gobjects.add(new ShapeGobject2(sh, new ColorHolder(color), null));
        }

        return gobjects;

    }

    public Group getFirstSubstring(String s) {
        var index = fixedIndex(string.indexOf(s));
        return this.subGroup(IntStream.rangeClosed(index, index + s.length() - 1)
                .boxed().toArray(Integer[]::new));

    }

    private int fixedIndex(int index) {
        int result = index;
        for (var el : spacemapping) {
            if (el > index) {
                break;
            }
            result--;
        }
        return result;
    }


    private static ArrayList<Integer> extractSpaceMapping(String s) {
        if (!s.contains(" ")) {
            new ArrayList<>();
        }
        ArrayList<Integer> result = new ArrayList<>();
        int aux = -1;
        while (true) {
            aux = s.indexOf(" ", ++aux);
            if (aux == -1) {
                break;
            }
            result.add(aux);
        }
        return result;
    }

    public String getString() {
        return string;
    }

    public Task typeEffect() {
        if (getGobjects().size() == 0) return new WaitTask(1);

        var colorHolders = this.getColors();
        var beforeColors = ColorHolder.toColorList(colorHolders);
        for (ColorHolder colorHolder : colorHolders) {
            var color = colorHolder.getColor();
            colorHolder.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0));
        }

        var taskList = new ArrayList<Task>();

        for (int i = 0; i < colorHolders.size(); i++) {

            int finalI = i;
            taskList.add(new ContextSetupTask(() -> {
                colorHolders.get(finalI).setColor(beforeColors.get(finalI));
                return new WaitTask((int) (Math.random() * 2) + 1);
            }));
        }

        return new SequenceTask(taskList);


    }

    public Task erase(int amount) {
        var taskList = new ArrayList<Task>();
        for (int i = 0; i < amount; i++) {
            taskList.add(new ContextSetupTask(() -> {
                var size = getGobjects().size();
                if (size >= 1) {
                    remove(size - 1);
                    string = string.substring(string.length() - 1);
                    this.spacemapping = extractSpaceMapping(string);
                }

                return new WaitTask((int) (1 + Math.random() * 3));
            }));
        }
        return new SequenceTask(taskList);
    }

    public Location getRef() {
        return ref;
    }


    @Override
    public List<Location> getRefereceLocations() {
        var locations = super.getRefereceLocations();
        locations.add(ref);
        return locations;
    }


    public StringGobject putInFront(String string, Font font, Color color) {
        return new StringGobject(string, font, getRef().plus(getBorders().getwidth(), 0), color);
    }
}
