package graphical.basics.gobject;

import graphical.basics.BackGround;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.task.Task;

public abstract class AnimPart {
    protected abstract void buildPresentation(Gobject... gobjects);

    public void add(Gobject gobject) {
        Presentation.staticReference.add(gobject);
    }

    public void add(Gobject... gs) {
        Presentation.staticReference.add(gs);
    }

    public void addBefore(Gobject referential, Gobject gobject) {
        Presentation.staticReference.addBefore(referential, gobject);
    }

    public void remove(Gobject gobject) {
        Presentation.staticReference.remove(gobject);
    }

    public void remove(Gobject... gobjects) {
        Presentation.staticReference.remove(gobjects);
    }

    public void removeAll() {
        Presentation.staticReference.removeAll();
    }

    public void execute(Task task) {
        Presentation.staticReference.execute(task);
    }

    public void execute(Task... tasks) {
        Presentation.staticReference.execute(tasks);
    }

    public void cut() {
        Presentation.staticReference.cut();
    }

    public int seconds(double seconds) {
        return Presentation.staticReference.seconds(seconds);
    }

    public Task paralel(Task... tasks) {
        return Presentation.staticReference.paralel(tasks);
    }

    public Task fadeOut(Gobject gobject, int steps) {
        return Presentation.staticReference.fadeOut(gobject, steps);
    }

    public Task fadeOut(Gobject gobject) {
        return Presentation.staticReference.fadeOut(gobject);
    }

    public Task wait(int steps) {
        return Presentation.staticReference.wait(steps);
    }

    public void switchOff() {
        Presentation.staticReference.switchOff();
    }

    public void switchOn() {
        Presentation.staticReference.switchOn();
    }

    public BackGround getBackGround() {
        return Presentation.staticReference.getBackGround();
    }

    public Camera getCamera() {
        return Presentation.staticReference.getCamera();
    }

    public PresentationConfig getPresentationConfig() {
        return Presentation.staticReference.getPresentationConfig();
    }
}
