import engine.entities.Entity;
import engine.managers.HitBox;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.math.num.Vector;

public class HitBoxMovementTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity entity = new Entity(new Vector(20, 20), new Vector(1000, 1000), new Vector(0, 0));
        Rectangle rectangle = new Rectangle(20, 20);
        Circle randomCircle = new Circle(20);
        randomCircle.setCenterX(500);
        randomCircle.setCenterY(500);
        HitBox hitBox = new HitBox(rectangle, entity.getTransform().getPosition(), "rectangle", entity.getTransform());
        Pane group = new Pane();
        group.getChildren().addAll(rectangle, randomCircle);
        Scene s = new Scene(group);
        primaryStage.setScene(s);
        primaryStage.show();

        for(int i = 0; i < 10; i++) {
            Thread.sleep(500);
            entity.update();
            System.out.println("X Entity position " + entity.getTransform().getPosition().at(0));
            System.out.println("HitBox X Position " + rectangle.getLayoutX());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
