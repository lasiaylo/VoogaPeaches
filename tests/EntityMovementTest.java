import engine.entities.Entity;
import engine.scripts.IScript;
import engine.scripts.defaults.ReverseDirection;
import javafx.application.Application;
import javafx.stage.Stage;
import util.math.num.Vector;

public class EntityMovementTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity e = new Entity(new Vector(0, 0), new Vector(100, 100), new Vector(5, 5));
        for(int i = 0; i < 10; i++) {
            e.update();
            System.out.println(e.getTransform().getPosition().at(0));
            if(i == 5) {
                IScript script = new ReverseDirection();
                e.addScript(script);
                e.update();
                e.getScripts().remove(script);

            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
