package authoring.GameWindow;

import com.google.firebase.database.FirebaseDatabase;
import engine.entities.Entity;
import engine.events.KeyPressEvent;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameWindow {

    private Scene scene;
    private SubScene sub;
    private Stage stage;
    private Camera camera;
    private Entity entity;
    private Group root;
    private Group gameGroup;
    private double CAMERASIZE;
    private Group hudGroup;

    public GameWindow(Entity entity){
        setupGameLayer();
        setupStage();
    }

    private void setupGameLayer() {
        sub = new SubScene(gameGroup,CAMERASIZE,CAMERASIZE);
        sub.setCamera(camera);
    }

    private void loadLevel(Entity level){
        Node game = level.getNodes().getChildren().get(0);
        Node hud = level.getNodes().getChildren().get(1);
        gameGroup = new Group(game);
        hudGroup = new Group(hud);
    }

    private void setupHUD(){
        root.getChildren().add(gameGroup);

//        Somehow only add the UI Layer
//        root.getChildren().add()
    }

    private void setupScene(){
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(e->new KeyPressEvent(e).recursiveFire(entity));
    }

    private void loadgame() { }

    private void setupStage() {
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}