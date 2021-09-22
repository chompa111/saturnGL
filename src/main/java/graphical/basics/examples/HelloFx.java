package graphical.basics.examples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class HelloFx extends Application {

     static GraphicsContext gc;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(1920, 1080);
         gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
        new Thread(()->{
            var q=new SVG2EXp();
            q.bufferedGraphics=new FXGraphics2D(gc);
            q.build();
        }).start();
    }

    public static void main(String[] args) {

        launch();


    }

}