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

    private void loginPressed(ActionEvent e) {
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.USER_SETTINGS);
        try {
            JSONObject blueprint = manager.readJSONFile(userTextField.getText());
            JSONToObjectConverter<User> converter = new JSONToObjectConverter<>(User.class);
            User user = converter.createObjectFromJSON(User.class,blueprint);
            PubSub.getInstance().publish("WORKSPACE_CHANGE", new WorkspaceChange(user.getWorkspaceName()));
            PubSub.getInstance().publish("THEME_MESSAGE", new ThemeMessage(user.getThemeName()));
        } catch (Exception error) {
            error.printStackTrace();
//
//
//            THROW AN ERROR
            System.out.println("wrong username, but you can keep playing I guess");
        }
        Stage menuStage = new Stage();
        Menu myMenu = new Menu(menuStage);
    }


    public Stage getStage() {
        return myStage;
    }

    private void updateTheme() {
        PubSub.getInstance().subscribe(
                "THEME_MESSAGE",
                (message) -> {
                    if (myArea.getStylesheets().size() >= 1) {
                        myArea.getStylesheets().remove(0);
                    }
                    myArea.getStylesheets().add(((ThemeMessage) message).readMessage());
                }
        );
        myArea.getStyleClass().add("panel");
    }

}
