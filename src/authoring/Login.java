package authoring;

import database.CurrentUser;
import database.User;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 * Login splash screen. Should give information about the user if there is a valid user object corresponding
 * to the username.
 *
 * @author Simran Singh
 * @author Kelly Zhang
 */
public class Login {

    private Stage myStage;
    private Scene myScene;
    private VBox myArea;
    private TextField userTextField;
    private TextField passwordField;

    public Login(Stage stage) {
        myStage = stage;
        myArea = createVBoxLayout();
        myScene = new Scene(myArea, 350,400);

        myStage.setScene(myScene);
        myStage.setResizable(false);
        myStage.setTitle("VoogaPeaches: Login");

        updateTheme();
    }

    private VBox createVBoxLayout() {
        VBox vbox = new VBox();

        vbox.setSpacing(10);
        vbox.setPadding(new Insets(5));
        vbox.setAlignment(Pos.CENTER_LEFT);

        Text userLabel = new Text("User Name");
        userTextField = new TextField();
        Text passLabel = new Text("Password");
        passwordField = new TextField();
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> loginPressed(e));

        vbox.getChildren().addAll(userLabel, userTextField, passLabel, passwordField, loginButton);

        return vbox;
    }

    /**
     * On the login, it reads the text that the user input. No password check currently. It tries to find a
     * JSON with the username, if it isn't there, it currently doesn't do anything, but if a JSON file exists,
     * it'll publish the current theme and workspace.
     *
     * @param e
     */
    private void loginPressed(ActionEvent e) {
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.USER_SETTINGS);
        try {
            JSONObject blueprint = manager.readJSONFile(userTextField.getText());
            JSONToObjectConverter<User> converter = new JSONToObjectConverter<>(User.class);
            User user = converter.createObjectFromJSON(User.class,blueprint);
            CurrentUser.currentUser = user;
//        #TODO Update the workspace properties files with the given information from user.
        } catch (Exception error) {
//            THROW AN ERROR
            CurrentUser.currentUser = new User("Default");
            System.out.println("wrong username, but you can keep playing I guess");
        }
        Stage menuStage = new Stage();
        Menu myMenu = new Menu(menuStage);
    }

    private void updateTheme() {
        myArea.getStylesheets().add("light.css"); //update from database
        myArea.getStyleClass().add("panel");
    }


    public Stage getStage() {
        return myStage;
    }

}
