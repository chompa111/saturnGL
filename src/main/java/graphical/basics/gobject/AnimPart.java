package graphical.basics.gobject;

import graphical.basics.BackGround;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.presentation.Animation;
import graphical.basics.presentation.AnimationStaticReference;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;
import graphical.basics.task.Task;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public abstract class AnimPart {
    protected abstract void buildPresentation(Gobject... gobjects);

    public void add(Gobject gobject) {
        AnimationStaticReference.staticReference.add(gobject);
    }

    public void add(Gobject... gs) {
        Animation.staticReference.add(gs);
    }

    public void addBefore(Gobject referential, Gobject gobject) {
        Animation.staticReference.addBefore(referential, gobject);
    }

    public void remove(Gobject gobject) {
        AnimationStaticReference.staticReference.remove(gobject);
    }

    public void remove(Gobject... gobjects) {
        Animation.staticReference.remove(gobjects);
    }

    public void removeAll() {
        Animation.staticReference.removeAll();
    }

    public void execute(Task task) {
        AnimationStaticReference.staticReference.execute(task);
    }

    public void execute(Task... tasks) {
        Animation.staticReference.execute(tasks);
    }

    public void cut() {
        Animation.staticReference.cut();
    }

    public int seconds(double seconds) {
        return AnimationStaticReference.staticReference.seconds(seconds);
    }

    public Task paralel(Task... tasks) {
        return Animation.staticReference.paralel(tasks);
    }

    public Task fadeOut(Gobject gobject, int steps) {
        return Animation.staticReference.fadeOut(gobject, steps);
    }

    public Task fadeOut(Gobject gobject) {
        return Animation.staticReference.fadeOut(gobject);
    }

    public Task wait(int steps) {
        return Animation.staticReference.wait(steps);
    }

    public void switchOff() {
        Animation.staticReference.switchOff();
    }

    public void switchOn() {
        Animation.staticReference.switchOn();
    }

    public BackGround getBackGround() {
        return Animation.staticReference.getBackGround();
    }

    public Camera getCamera() {
        return Animation.staticReference.getCamera();
    }

    public PresentationConfig getPresentationConfig() {
        return Animation.staticReference.getPresentationConfig();
    }

    public Runnable addBehavior(Runnable task) {
        return AnimationStaticReference.staticReference.addBehavior(task);
    }

    public <T> Runnable addBehavior(T metadata, Consumer<T> task) {
        return AnimationStaticReference.staticReference.addBehavior(metadata, task);
    }

    public void removeBehavior(Runnable r) {
        AnimationStaticReference.staticReference.removeBehavior(r);
    }

    public void addDragBehavior(Gobject gobject) {
        RTAnimation.staticReference.addDragBehavior(gobject);
    }

    public void addClickListener(Gobject gobject, Runnable r) {
        RTAnimation.staticReference.addClickListener(gobject, r);
    }

    public void addKeyPressedListener(Consumer<KeyEvent> keyEventConsumer) {
        RTAnimation.staticReference.addKeyPressedListener(keyEventConsumer);
    }

    public void addKeyReleasedListener(Consumer<KeyEvent> keyEventConsumer) {
        RTAnimation.staticReference.addKeyReleasedListener(keyEventConsumer);
    }
}
