package authoring;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.pubsub.PubSub;
import util.pubsub.messages.ThemeMessage;

import java.io.File;

public class Login {

    private Scene myScene;
    private Group myRoot;

    public Login() {
        myRoot = new Group();
        myScene = new Scene(myRoot, 500,500);

        updateTheme();
    }

    public Scene getScene() {
        return myScene;
    }

    private void updateTheme() {
        PubSub.getInstance().subscribe(
                PubSub.Channel.THEME_MESSAGE,
                (message) -> {
                    if (myRoot.getStylesheets().size() >= 1) {
                        myRoot.getStylesheets().remove(0);
                    }
                    myRoot.getStylesheets().add(((ThemeMessage) message).readMessage());
                }
        );
    }

}
