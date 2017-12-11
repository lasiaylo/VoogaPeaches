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

    public GameWindow(Entity entity){
        System.out.println(entity);
        setupGameLayer();
        setupStage();
    }

    private void setupGameLayer() {
        Group group = new Group();
        group.getChildren().add(entity.getNodes().getChildren().get(0));
    }

    private void setupScene(){

        Scene scene = new Scene(group);
        scene.setOnKeyPressed(e->new KeyPressEvent(e).recursiveFire(entity));
    }

    private void loadgame() { }

    private void setupStage() {
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}