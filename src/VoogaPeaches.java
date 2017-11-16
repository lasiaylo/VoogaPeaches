import authoring.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launches the program.
 * @author Brian Nieves
 */
public class VoogaPeaches extends Application {

    private static final String TITLE = "VoogaSalad: A Programmers for Peaches Production";

    public void start(Stage stage) {
        stage.setTitle(TITLE);
        stage.setMaximized(true);
        stage.setResizable(false);
        Screen kitty = new Screen(stage);
    }

    public static void main(String[] args){
        launch();
    }
}
