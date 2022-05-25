package graphical.basics.gobject;

import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.task.ParalelTask;
import graphical.basics.task.SequenceTask;
import graphical.basics.task.Task;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Text extends Group {

    Color color;

    Font font;

    Location location;

    List<StringGobject> lines;

    public Text(Color color, Font font, Location location) {
        this.color = color;
        this.font = font;
        this.location = location;
        lines = new ArrayList<>();
    }

    public void newLine(String line) {
        var newLine = new StringGobject(line, font, location.plus(0, lines.size() * font.getSize() * 1.15), color);
        lines.add(newLine);
        if (!line.isBlank()) {
            add(newLine);
        }
    }

    public void newLine(int index, String line) {
        var newLine = new StringGobject(line, font, location.plus(0, index * font.getSize() * 1.15), color);

        for (int i = index; i < lines.size(); i++) {
            lines.get(i).changeSetPosition(0, font.getSize());

        }

        lines.add(index, newLine);
        add(index, newLine);
    }

    public StringGobject replaceLine(int index, String line) {
        var newLine = new StringGobject(line, font, location.plus(0, index * font.getSize() * 1.15), color);
        var removedLine = lines.remove(index);
        lines.add(index, newLine);
        remove(index);
        add(index, newLine);

        return removedLine;
    }

    public Task newLineAnimated(int index, String line) {

        var list = new ArrayList<Task>();
        var newLine = new StringGobject(line, font, location.plus(0, index * font.getSize() * 1.15), color);

        for (int i = index; i < lines.size(); i++) {
            list.add(lines.get(i).move(0, font.getSize() * 1.15));

        }

        return new ParalelTask(list).afterConclusion(() -> {
            lines.add(index, newLine);
            add(index, newLine);
        });
    }

    public List<StringGobject> getLines() {
        return lines;
    }

    public StringGobject getLine(int index) {
        return lines.get(index);
    }

    public Task typingEffect() {
        var taskList = new ArrayList<Task>();
        for (StringGobject s : lines) {
            taskList.add(s.typeEffect());
//            if(s.getGobjects().size()<4){
//                // to short
//                taskList.add(new WaitTask(7));
//            }
        }
        return new SequenceTask(taskList);
    }

    @Override
    public List<Location> getReferenceLocations() {
        var locations = super.getReferenceLocations();
        locations.add(location);
        return locations;
    }

    @Override
    public LocationPair borderWhenEmpty() {
        return new LocationPair(location, location);
    }
}
