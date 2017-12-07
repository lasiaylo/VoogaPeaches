//import authoring.Screen;
import authoring.Login;
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

    private static final String TITLE = "VoogaSalad: A Programmers for Peaches Production";

    private Screen kitty;

    public void start(Stage stage) {
        Login myLogin = new Login();

        Menu myMenu = new Menu();
        myMenu.getStage().show();

        stage.setTitle(TITLE);
        stage.setMaximized(true);
        stage.setResizable(false);
        kitty = new Screen(stage);
    }

    @Override
    public void stop() throws Exception{
//        kitty.save();
        super.stop();
        FirebaseConnector.closeFirebaseApp();
    }

    public static void main(String[] args){
        launch();
    }
}
