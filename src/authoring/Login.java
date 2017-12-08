package authoring;

import database.User;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;
import util.pubsub.PubSub;
import util.pubsub.messages.ThemeMessage;
import util.pubsub.messages.WorkspaceChange;

import java.io.File;

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
//    private TextField passwordField;

    public Login(Stage stage) {
        myStage = stage;
        myArea = createVBoxLayout();
        createLoginButtons();

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
//        Text passLabel = new Text("Password");
//        passwordField = new TextField();

        vbox.getChildren().addAll(userLabel, userTextField/*, passLabel, passwordField*/);

        return vbox;
    }

    private void createLoginButtons() {
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> loginPressed(e));
        Button newUserButton = new Button("New Account");
        newUserButton.setScaleX(0.75);
        newUserButton.setScaleY(0.75);
        newUserButton.setOnAction(e -> newUserPressed(e));
        myArea.getChildren().addAll(loginButton, newUserButton);
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
            PubSub.getInstance().publish("WORKSPACE_CHANGE", new WorkspaceChange(user.getWorkspaceName()));
            PubSub.getInstance().publish("THEME_MESSAGE", new ThemeMessage(user.getThemeName()));
            Stage menuStage = new Stage();
            Menu myMenu = new Menu(menuStage, user);
//        #TODO Update the workspace properties files with the given information from user.
        } catch (Exception error) {
            error.printStackTrace();
//
//
//            THROW AN ERROR
            System.out.println("no user exists or wrong password");
        }
    }

    private void newUserPressed(ActionEvent e) {
        Stage newAccount = new Stage();
        newAccount.setTitle("VoogaPeaches: New Account");
        VBox newArea = createVBoxLayout();
        Scene newScene = new Scene(newArea, 500, 300);

        Button createButton = new Button("Go");
        String newUsername = ((TextField) newArea.getChildren().get(1)).getText();
        createButton.setOnAction(f -> createPressed(f, newUsername, newAccount));

        newArea.getChildren().add(createButton);
        newAccount.setScene(newScene);
        newAccount.show();
    }

    private void createPressed(ActionEvent e, String username, Stage stage) {
        //TODO: add the user to the userbase and check if it has already been created
        User newUser = new User(username);
        stage.close();
    }

    private void updateTheme() {
        myArea.getStylesheets().add("light.css"); //update from database
        myArea.getStyleClass().add("panel");
    }


    public Stage getStage() {
        return myStage;
    }

}
