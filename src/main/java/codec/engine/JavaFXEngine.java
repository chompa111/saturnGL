package codec.engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class JavaFXEngine implements JavaGraphicEngine {
    private final Graphics graphics;
    private final GraphicsContext jfxGraphics;
    private final int width;
    private final int height;
    private Group root;

    public JavaFXEngine(int width, int height) {
        try {
            initializeJavaFXWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final var canvas = new Canvas(width, height);
        jfxGraphics = canvas.getGraphicsContext2D();
        graphics = new FXGraphics2D(jfxGraphics);
        root.getChildren().add(canvas);

        this.width = width;
        this.height = height;
    }

    @Override
    public BufferedImage getActualFrame() {
        final var parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        final var task = new FutureTask<>(() -> root.snapshot(parameters, null));
        Platform.runLater(task);
        try {
            return SwingFXUtils.fromFXImage(task.get(), null);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public void clear() {
        jfxGraphics.clearRect(0, 0, width, height);
    }

    public static class FXStarter extends Application {
        private static final CountDownLatch latch = new CountDownLatch(1);

        public static void awaitFXToolkit() throws InterruptedException {
            latch.await();
        }

        public void init() {
            latch.countDown();
        }

        public void start(final Stage primaryStage) {
        }
    }

    public final void initializeJavaFXWindow() throws Exception {
        (new Thread(() -> Application.launch(FXStarter.class))).start();
        FXStarter.awaitFXToolkit();
        final var task = new FutureTask<>(() -> {
            root = new Group();
            return 1;
        });
        Platform.runLater(task);
        task.get();
    }
}
