package graphical.basics.miscelaneous;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creating a Box (Cube)
        Box cube = new Box(100, 100, 100);

        // Creating a material to color the cube
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.RED); // Set diffuse color to red

        // Applying the material to the cube
        cube.setMaterial(material);

        // Centering the cube in the scene
        cube.setTranslateX(300); // half of the scene width minus half of the cube width
        cube.setTranslateY(200); // half of the scene height minus half of the cube height

        // Tilt the cube to reveal a bit of the top face
        cube.setRotationAxis(Rotate.X_AXIS);
        cube.setRotate(30);

        // Creating a rotation animation for the cube (Clockwise)
        RotateTransition rotateTransitionCW = new RotateTransition(Duration.seconds(4), cube);
        rotateTransitionCW.setAxis(Rotate.Y_AXIS);
        rotateTransitionCW.setByAngle(360);
        rotateTransitionCW.setCycleCount(Animation.INDEFINITE);
        rotateTransitionCW.play();

        // Creating a rotation animation for the cube (Counterclockwise)
        RotateTransition rotateTransitionCCW = new RotateTransition(Duration.seconds(4), cube);
        rotateTransitionCCW.setAxis(Rotate.Y_AXIS);
        rotateTransitionCCW.setByAngle(-360);
        rotateTransitionCCW.setCycleCount(Animation.INDEFINITE);
        rotateTransitionCCW.play();

        // Creating a light source
        javafx.scene.PointLight light = new javafx.scene.PointLight(Color.WHITE);
        light.setTranslateX(200);
        light.setTranslateY(-100);
        light.setTranslateZ(-200);

        // Creating a Group object
        Group root = new Group();

        // Adding the cube and light to the Group
        root.getChildren().addAll(cube, light);

        // Creating a Scene by passing the group object, height, and width
        Scene scene = new Scene(root, 600, 400, true);

        // Creating a PerspectiveCamera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        //scene.setCamera(camera);

        // Setting the title to Stage.
        primaryStage.setTitle("3D Cube Example");

        // Adding the Scene to the Stage
        primaryStage.setScene(scene);

        // Displaying the contents of the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

