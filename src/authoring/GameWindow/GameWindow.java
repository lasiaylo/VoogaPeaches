package authoring.GameWindow;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameWindow {

    private Scene scene;
    private Stage stage;

    public GameWindow(){
        loadgame();
        setupStage();
    }

    private void loadgame() {
    }

    private void setupStage() {
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
