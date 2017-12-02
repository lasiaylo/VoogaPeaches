import engine.collisions.HitBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.math.num.Vector;

import java.util.HashMap;
import java.util.Map;

public class HitBoxTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle circle = new Circle(30);
        Rectangle rectangle = new Rectangle(20, 20);
        Vector circleOffset = new Vector(30, 30);
        Vector rectOffset = new Vector(20, 20);
        Map<Shape, Vector> offsets = new HashMap<>();
        offsets.put(circle, circleOffset);
        offsets.put(rectangle, rectOffset);

        Vector position = new Vector(50, 50);
        HitBox hitBox = new HitBox(position, offsets, "Hell Yeah");

        Group group = new Group();
        group.getChildren().addAll(hitBox.getShapes());
        Scene s = new Scene(group);
        primaryStage.setScene(s);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            hitBox.move(new Vector(50*Math.random(), 50*Math.random()));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
