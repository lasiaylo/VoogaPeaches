import engine.entities.Entity;
import engine.visualization.GameVisualizer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VizTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity root = new Entity();

        Entity child1 = new Entity(root);
        Entity child2 = new Entity(root);

        Entity child11 = new Entity(child1);
        Entity child12 = new Entity(child2);

        GameVisualizer gameVisualizer = new GameVisualizer(root);

        Scene s = new Scene(gameVisualizer.getGroup());
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
