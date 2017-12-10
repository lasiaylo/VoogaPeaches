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

        for (int i = 0; i < 5; i++){
            Entity e = new Entity(root);
        }


        Entity grandChild1 = new Entity(child1);
        Entity grandChild2 = new Entity(child1);

        Entity grandChild3 = new Entity(child2);
        Entity grandChild4 = new Entity(child2);

        GameVisualizer gameVisualizer = new GameVisualizer(root);

        Scene s = new Scene(gameVisualizer.getGroup());
        primaryStage.setScene(s);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}