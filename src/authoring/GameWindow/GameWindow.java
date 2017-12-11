package authoring.GameWindow;

import com.google.firebase.database.FirebaseDatabase;
import engine.entities.Entity;
import engine.events.KeyPressEvent;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.stage.Stage;

public class GameWindow {

    private Scene scene;
    private SubScene sub;
    private Stage stage;
    private GameCamera camera;
    private Entity entity;
    private Group root;
    private Group gameGroup;
    private double CAMERASIZE;

    public GameWindow(Entity entity){
        System.out.println(entity);
        setupGameLayer();
        setupStage();
    }

    private void setupGameLayer() {
        gameGroup = new Group();
//        Somehow only add the Game layer
//        group.getChildren().add()
        sub = new SubScene(gameGroup,CAMERASIZE,CAMERASIZE);
        sub.setCamera(camera.getCamera());
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