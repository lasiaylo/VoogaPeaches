package authoring;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.pubsub.PubSub;
import util.pubsub.messages.ThemeMessage;

import java.io.File;

public class Login {

    private Stage myStage;
    private Scene myScene;
    private Group myRoot;
    private VBox myArea;

    public Login() {
        myStage = new Stage();
        myArea = new VBox();
        myRoot = new Group();
        myRoot.getChildren().add(myArea);
        myScene = new Scene(myRoot, 500,500);

        myStage.setScene(myScene);
        myStage.setResizable(false);
        myStage.setTitle("VoogaPeaches: Login");
        myStage.show();

        updateTheme();
    }

    public Scene getScene() {
        return myScene;
    }

    private void updateTheme() {
        PubSub.getInstance().subscribe(
                "THEMES",
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
