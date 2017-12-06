import authoring.panels.tabbable.HitBoxPanel;
import engine.entities.Entity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class HitBoxPanelTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        HitBoxPanel hbPanel = new HitBoxPanel();

        Entity e = new Entity();
        e.getNodes().getChildren().add(new Circle(20));
        e.setProperty("x", 20.);
        e.setProperty("y", 20.);
        hbPanel.setEntity(e);

        Scene s = new Scene(hbPanel.getRegion());
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
