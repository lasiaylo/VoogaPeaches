package authoring;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import util.pubsub.PubSub;
import util.pubsub.messages.ThemeMessage;

import java.io.File;

public class Login {

    private Scene myScene;
    private Group myRoot;
    private VBox myArea;

    public Login() {
        myArea = new VBox();
        myRoot = new Group();
        myRoot.getChildren().add(myArea);
        myScene = new Scene(myRoot, 500,500);

        updateTheme();
    }

    public Scene getScene() {
        return myScene;
    }

    private void updateTheme() {
        /*PubSub.getInstance().subscribe(//TODO pubsub
                PubSub.Channel.THEME_MESSAGE,
                (message) -> {
                    if (myArea.getStylesheets().size() >= 1) {
                        myArea.getStylesheets().remove(0);
                    }
                    myArea.getStylesheets().add(((ThemeMessage) message).readMessage());
                }
        );*/
        myArea.getStyleClass().add("panel");
    }

}
