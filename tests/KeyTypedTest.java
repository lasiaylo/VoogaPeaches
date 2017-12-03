import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KeyTypedTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Scene s = new Scene(group);
        primaryStage.setScene(s);
        group.setOnKeyTyped(e -> System.out.println("hell yeah"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
