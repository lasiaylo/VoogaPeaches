package engine.camera;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CameraInvestigation extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle circle = new Circle(30);
        circle.setCenterX(30);
        circle.setCenterY(30);
        Group group = new Group();
        group.getChildren().add(circle);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100000), e -> {
            circle.setCenterX(circle.getCenterX() + 1);
            circle.setCenterY(circle.getCenterY() + 1);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
