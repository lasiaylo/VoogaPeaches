import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class KeyTypedTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Rectangle rectangle = new Rectangle(20, 20);
        group.getChildren().add(rectangle);
        Scene s = new Scene(group);
        primaryStage.setScene(s);
        group.setOnMouseClicked(e -> System.out.println("hell yeah"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
