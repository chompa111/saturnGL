package codec.engine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class JavaFXEngine implements JavaGraphicEngine {
    static Group root = new Group();

    ;
    Graphics graphics;
    GraphicsContext jfxgraphics;
    private int width;
    private int height;

    public JavaFXEngine(int width, int height) {
        try {
            initializeJavaFXWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Canvas canvas = new Canvas(width, height);
        jfxgraphics = canvas.getGraphicsContext2D();
        graphics = new FXGraphics2D(jfxgraphics);//adapter
        root.getChildren().add(canvas);

        this.width = width;
        this.height = height;

    }

    @Override
    public BufferedImage getActualFrame() {
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        FutureTask<WritableImage> task = new FutureTask(new Callable<WritableImage>() {
            public WritableImage call() throws Exception {
                return root.snapshot(parameters, (WritableImage) null);
            }
        });
        Platform.runLater(task);
        WritableImage img2 = null;
        try {
            img2 = (WritableImage) task.get();
            return SwingFXUtils.fromFXImage(img2, (BufferedImage) null);
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
        jfxgraphics.clearRect(0, 0, width, height);
    }

    public static class FXStarter extends Application {
        public static Stage stage;
        private static final CountDownLatch latch = new CountDownLatch(1);

        public FXStarter() {
        }

        public static void awaitFXToolkit() throws InterruptedException {
            latch.await();
        }

        public void init() {
            latch.countDown();
        }

        public void start(Stage primaryStage) {
            stage = primaryStage;
            stage.setTitle("JMathAnim preview window");
        }
    }

    public final void initializeJavaFXWindow() throws Exception {
        (new Thread(() -> {
            Application.launch(FXStarter.class, new String[0]);
        })).start();
        FXStarter.awaitFXToolkit();
        FutureTask<Integer> task = new FutureTask(new Callable<Integer>() {
            public Integer call() throws Exception {
                root = new Group();
                return 1;
            }
        });
        Platform.runLater(task);
        task.get();
    }


}
