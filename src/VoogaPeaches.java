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

    private Screen kitty;

    public void start(Stage stage) {
        /*Menu menuScreen = new Menu();
        Stage loginStage = new Stage();
        Login loginScreen = new Login();
        loginStage.setTitle(TITLE);
        loginStage.setScene(loginScreen.getScene());
        loginStage.show();*/
        stage.setTitle(TITLE);
        stage.setMaximized(true);
        stage.setResizable(false);
        kitty = new Screen(stage);
    }

    @Override
    public void stop() throws Exception{
        kitty.save();
        FirebaseConnector.closeFirebaseApp();
        super.stop();

    }

    public static void main(String[] args){
        launch();
    }
}
