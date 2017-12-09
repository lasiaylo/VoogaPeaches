package authoring;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.pubsub.PubSub;
import util.pubsub.messages.StringMessage;

public class Login {

    private Stage myStage;
    private Scene myScene;
    private VBox myArea;

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
        TextField userTextField = new TextField();
        Text passLabel = new Text("Password");
        TextField passwordField = new TextField();
        Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage menuStage = new Stage();
                Menu myMenu = new Menu(menuStage);
                myStage.close();
            }
        });

        vbox.getChildren().addAll(userLabel, userTextField, passLabel, passwordField, loginButton);

        return vbox;
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
                    myArea.getStylesheets().add(((StringMessage) message).readMessage());
                }
        );
        myArea.getStyleClass().add("panel");
    }

}
