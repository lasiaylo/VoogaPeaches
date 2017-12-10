import authoring.panels.tabbable.GameGraphPanel;
import authoring.panels.tabbable.HitBoxPanel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GameGraphPanelTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group g = new Group();
        Circle c = new Circle(50);
        c.setFill(Color.BLACK);
        g.getChildren().add(c);

        GameGraphPanel graphPanel = new GameGraphPanel();
        graphPanel.setGroup(g);

        Scene s = new Scene(graphPanel.getRegion());
        primaryStage.setScene(s);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(300);
        primaryStage.setMaxWidth(300);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
