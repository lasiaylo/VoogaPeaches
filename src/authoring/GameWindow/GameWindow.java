package authoring.GameWindow;

import engine.Engine;
import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.math.num.Vector;

import java.awt.*;

public class GameWindow {

    private Engine engine;
    private Entity entity;
    private Scene scene;
    private Group group;
    private ScrollPane pane;

    public GameWindow(Entity entity){
        engine = new Engine(entity, 30, true);
        loadgame();
        setupStage();
    }

    private void loadgame() {
        pane = engine.getCameraView(new Vector(0,0),new Vector(500,500));
        engine.play();
    }

    private void setupStage() {
        Stage stage = new Stage();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}