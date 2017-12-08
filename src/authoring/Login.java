package authoring;

import database.User;
import database.firebase.DatabaseConnector;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import database.jsonhelpers.JSONToObjectConverter;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.VoogaPeaches;
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

    public Login(Stage stage) {
        myStage = stage;
        myArea = createVBoxLayout();
        myScene = new Scene(myArea, 350,400);

        myStage.setScene(myScene);
        myStage.setResizable(false);
        myStage.setTitle("main.VoogaPeaches: Login");

        updateTheme();
    }

    private VBox createVBoxLayout() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(5));
        vbox.setAlignment(Pos.CENTER_LEFT);
        Text userLabel = new Text("User Name");
        userTextField = new TextField();
        GridPane grid = new GridPane();
        grid.setHgap(10);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> loginPressed());
        grid.add(loginButton, 0,0);

        Button signupButton = new Button("Create Profile");
        signupButton.setOnAction(e -> createAccount() );
        grid.add(signupButton, 1, 0);

        vbox.getChildren().addAll(userLabel, userTextField, grid);
        return vbox;
    }

    private void createAccount(){
        if(!userTextField.getText().trim().isEmpty()){
            User newUser = new User(userTextField.getText().trim());
            JSONDataManager manager = new JSONDataManager(JSONDataFolders.USER_SETTINGS);
            manager.writeJSONFile(userTextField.getText().trim(), JSONHelper.JSONForObject(newUser));
            VoogaPeaches.changeUser(newUser);
            Stage menuStage = new Stage();
            Menu myMenu = new Menu(menuStage);
        }
    }


    /**
     * On the login, it reads the text that the user input. No password check currently. It tries to find a
     * JSON with the username, if it isn't there, it currently doesn't do anything, but if a JSON file exists,
     * it'll publish the current theme and workspace.
     */
    private void loginPressed() {
        DatabaseConnector<User> connector = new DatabaseConnector<>(User.class);
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.USER_SETTINGS);
        JSONObject blueprint = manager.readJSONFile(userTextField.getText());
        if (blueprint != null) {
            JSONToObjectConverter<User> converter = new JSONToObjectConverter<>(User.class);
            User user = converter.createObjectFromJSON(User.class, blueprint);
            VoogaPeaches.changeUser(user);
            Stage menuStage = new Stage();
            Menu myMenu = new Menu(menuStage);
        }
    }

    private void updateTheme() {
        myArea.getStylesheets().add("light.css"); //update from database
        myArea.getStyleClass().add("panel");
    }


    public Stage getStage() {
        return myStage;
    }

}
