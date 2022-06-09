package graphical.basics.gobject;

import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.Presentation;
import graphical.basics.task.ContextSetupTask;
import graphical.basics.task.Task;
import graphical.basics.task.WaitTask;

import java.awt.*;
import java.util.List;

public class CodeBlock extends Group {

    private Location location;

    private static final double EXTRA_SPACEMENT = 1.15;

    private Rect debbugLine;

    private Rect background;
    private Rect backgroundShadow;

    private Rect gutter;
    private Text textGutter;
    private Line gutterLine;

    private Text text;

    private JavaHilighter javaHilighter = new JavaHilighter();

    private Text textComment;

    int lineCounter = 1;
    int debuggerCurrentLine = 1;

    private int textSize = 30;


    public CodeBlock(Color color, Font font, Location location) {
        text = new Text(color, font, location);
        background = new Rect(text.getBorders().getL1().plus(-50, -50), text.getBorders().getL2().plus(50, 50), JavaHilighter.INTELLIJ_BACKGROUND);
        //  backgroundShadow = new Rect(text.getBorders().getL1().plus(-100, -100), text.getBorders().getL2().plus(0, 0), JavaHilighter.INTELLIJ_BACKGROUND);
        // add(backgroundShadow);
        add(background);
        add(text);
    }

    public CodeBlock(Location location, int textSize, int width) {
        this.textSize = textSize;
        this.location = location;
        text = new Text(JavaHilighter.INTELLIJ_GRAY, Fonts.JETBRAINS_MONO.deriveFont((float) textSize), location);
        textComment = new Text(Color.gray, Fonts.JETBRAINS_MONO.deriveFont((float) textSize), location.plus(width - 300, 0));
        textGutter = new Text(new Color(92, 90, 90), Fonts.JETBRAINS_MONO.deriveFont((float) textSize), location.plus(-70, 0));
        background = new Rect(location.plus(-80, -textSize * EXTRA_SPACEMENT - 10), location.plus(width, 0), JavaHilighter.INTELLIJ_BACKGROUND);
        backgroundShadow = new Rect(location.plus(-80, -textSize * EXTRA_SPACEMENT - 10).plus(-20, -20), location.plus(width, 0).plus(-20, -20), new Color(0, 0, 40, 150));
        gutter = new Rect(location.plus(-80, -textSize * EXTRA_SPACEMENT - 10), location.plus(-5, 0), JavaHilighter.GUTTER_COLOR);
        gutterLine = new Line(location.plus(-5, -textSize * EXTRA_SPACEMENT - 10), location.plus(-5, 0), Color.gray);
        add(backgroundShadow);
        add(background);
        add(gutter);
        add(gutterLine);


        add(text);
        add(textGutter);
        add(textComment);
    }

    public void newLine(String line) {
        text.newLine(line);
        textGutter.newLine("" + lineCounter);
        javaHilighter.colorize(text);
        background.getLowerRightPoint().setY(background.getLowerRightPoint().getY() + textSize * 1.15);
        backgroundShadow.getLowerRightPoint().setY(backgroundShadow.getLowerRightPoint().getY() + textSize * 1.15);
        gutter.getLowerRightPoint().setY(gutter.getLowerRightPoint().getY() + textSize * 1.15);
        gutterLine.getP2().setY(gutterLine.getP2().getY() + textSize * 1.15);
        lineCounter++;
    }

    public void newLine(int index, String line) {
        text.newLine(index, line);
        background.getLowerRightPoint().setY(background.getLowerRightPoint().getY() + textSize * 1.15);
        backgroundShadow.getLowerRightPoint().setY(backgroundShadow.getLowerRightPoint().getY() + textSize * 1.15);
        gutter.getLowerRightPoint().setY(gutter.getLowerRightPoint().getY() + textSize * 1.15);
        gutterLine.getP2().setY(gutterLine.getP2().getY() + textSize * 1.15);
        lineCounter++;
    }


    public StringGobject replaceLine(int index, String line) {
        return text.replaceLine(index, line);
    }

    public Task newLineAnimated(int index, String line) {
        textGutter.newLine("" + lineCounter);
        var newNumber = textGutter.getLine(textGutter.lines.size() - 1);
        return text.newLineAnimated(index, line)
                .parallel(background.getLowerRightPoint().move(0, textSize * 1.15, Presentation.staticReference.seconds(1)))
                .parallel(backgroundShadow.getLowerRightPoint().move(0, textSize * 1.15, Presentation.staticReference.seconds(1)))
                .parallel(gutter.getLowerRightPoint().move(0, textSize * 1.15, Presentation.staticReference.seconds(1)))
                .parallel(gutterLine.getP2().move(0, textSize * 1.15, Presentation.staticReference.seconds(1)))
                .parallel(Animation.fadeInGrow(newNumber, Presentation.staticReference.seconds(1)))
                .afterConclusion(() -> lineCounter++);
    }

    public Task newLinesAnimated(int index, String... newLines) {
        var amount = newLines.length;

        return text.newLinesAnimated(index, newLines)
                .parallel(background.getLowerRightPoint().move(0, amount * textSize * 1.15, Presentation.staticReference.seconds(1)))
                .parallel(backgroundShadow.getLowerRightPoint().move(0, amount * textSize * 1.15, Presentation.staticReference.seconds(1)))
                .parallel(gutter.getLowerRightPoint().move(0, amount * textSize * 1.15, Presentation.staticReference.seconds(1)))
                .parallel(gutterLine.getP2().move(0, amount * textSize * 1.15, Presentation.staticReference.seconds(1)))
                .parallel(new WaitTask(Presentation.staticReference.seconds(0.5) + 1)
                                .andThen(() -> {
                                    for (int i = 0; i < newLines.length; i++) {
                                        textGutter.newLine("" + (lineCounter + i));
                                    }
                                    return textGutter.getLinesAsGroup(lineCounter - 1, lineCounter - 2 + amount).onChildren(x -> Animation.fadeInGrow(x, Presentation.staticReference.seconds(1)));
                                }))
                .afterConclusion(() -> lineCounter += amount);
    }

    public void addDebugLine() {
        //  debbugLine = new Rect(text.getBorders().getL1().plus(-80, 0), text.getBorders().getL1().plus(background.getBorders().getwidth() - 80, 30 * 1.15), JavaHilighter.INTELLIJ_DEBUGGER_COLOR);
        debbugLine = new Rect(location.plus(-80, -textSize * 1.15).plus(0, 5), location.plus(background.getBorders().getwidth() - 80, 0).plus(0, 7), JavaHilighter.INTELLIJ_DEBUGGER_COLOR);
        add(debbugLine);
    }

    public void setDebugline(int line) {
        var delta = line - debuggerCurrentLine;
        debbugLine.changeSetPosition(0, delta * textSize * EXTRA_SPACEMENT);
        debuggerCurrentLine = line;
    }

    public void debuggNextLine() {
        setDebugline(debuggerCurrentLine + 1);
    }

    public Task debuggNextLineAnimated(int frames) {
        return setDebuglineAnimated(debuggerCurrentLine + 1, frames);
    }

    public Task setDebuglineAnimated(int line, int frames) {
        return new ContextSetupTask(() -> {
            var delta = line - debuggerCurrentLine;
            debuggerCurrentLine = line;
            return debbugLine.move(0, delta * textSize * EXTRA_SPACEMENT, frames);
        });
    }


    public Rect getDebbugLine() {
        return debbugLine;
    }

    public Rect getBackground() {
        return background;
    }

    public Rect getBackgroundShadow() {
        return backgroundShadow;
    }

    public Rect getGutter() {
        return gutter;
    }

    public Text getTextGutter() {
        return textGutter;
    }

    public Line getGutterLine() {
        return gutterLine;
    }

    public Text getText() {
        return text;
    }

    public JavaHilighter getJavaHilighter() {
        return javaHilighter;
    }

    public Text getTextComment() {
        return textComment;
    }
}
