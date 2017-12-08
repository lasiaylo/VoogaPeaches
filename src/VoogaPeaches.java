//import authoring.Screen;
import authoring.Login;
import authoring.Screen;
import database.firebase.FirebaseConnector;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launches the program.
 * @author Brian Nieves
 */
public class VoogaPeaches extends Application {

    @Override
	public void start(Stage stage) {
//        Login myLogin = new Login(stage);
//        myLogin.getStage().show();
        Screen kitty = new Screen(stage);

    }

    @Override
    public void stop() throws Exception{
        //TODO: save the authoring environment when that stage is closed
        super.stop();
        FirebaseConnector.closeFirebaseApp();
    }

    public static void main(String[] args){
        launch();
    }
}
