import authoring.Menu;
import authoring.Screen;
import database.firebase.FirebaseConnector;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launches the program.
 * @author Brian Nieves
 */
public class VoogaPeaches extends Application {

    private static final String TITLE = "VoogaPeaches: A Programmers for Peaches Production";

    private Menu myMenu;
    private Screen kitty;

    public void start(Stage stage) {
        myMenu = new Menu();
        stage.setTitle(TITLE);
        stage.setScene(myMenu.getScene());
//        stage.setMaximized(true);
//        stage.setResizable(false);
//        kitty = new Screen(stage);
    }

    @Override
    public void stop() throws Exception{
        kitty.save();
        super.stop();
        FirebaseConnector.closeFirebaseApp();
    }

    public static void main(String[] args){
        launch();
    }
}
