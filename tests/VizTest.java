import engine.entities.Entity;
import engine.visualization.GameVisualizer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VizTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity root = new Entity();
        for (int i = 0; i < 5; i++){
            Entity child = new Entity(root);
            for(int j = 0; j < 3; j++){
                Entity grandchild = new Entity(child);
                for(int k = 0; k < 5; k++){
                    Entity grandgrandchild = new Entity(grandchild);
                }
            }
        }
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