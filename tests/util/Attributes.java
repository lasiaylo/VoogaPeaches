import authoring.SplitScreen;
import javafx.stage.Stage;

public class AttributesTester extends Application {
    private static final String TITLE = "VoogaSalad: A Programmers for Peaches Production";

    public void start(Stage stage) {
        stage.setTitle(TITLE);
        stage.setMaximized(true);
        stage.setResizable(false);
        SplitScreen kitty = new SplitScreen(stage);
    }

    public static void main(String[] args){
        launch();
    }
}
