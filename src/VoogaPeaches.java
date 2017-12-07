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


    public void start(Stage stage) {
        Login myLogin = new Login(stage);
        myLogin.getStage().show();
    }

    @Override
    public void stop() throws Exception{
        //TODO: save the authoring envirnment when that stage is closes
//        kitty.save();
        super.stop();
        FirebaseConnector.closeFirebaseApp();
    }

    public static void main(String[] args){
        launch();
    }
}
