import engine.entities.Entity;
import javafx.application.Application;
import javafx.stage.Stage;
import util.math.num.Vector;

public class EntityMovementTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity e = new Entity(new Vector(0, 0), new Vector(5, 5), new Vector(1, 1));
        for(int i = 0; i < 10; i++) {
            e.update();
            System.out.println(e.getTransform().getPosition().at(0));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
