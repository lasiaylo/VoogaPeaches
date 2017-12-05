package authoring;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Login {

    private Scene myScene;
    private Group myRoot;

    public Login() {
        myRoot = new Group();
        myScene = new Scene(myRoot, 500,500);
    }

    public Scene getScene() {
        return myScene;
    }

}
