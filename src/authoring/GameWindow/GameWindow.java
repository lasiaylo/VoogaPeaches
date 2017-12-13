package authoring.GameWindow;

import authoring.PanelController;
import engine.Engine;
import engine.entities.Entity;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.VoogaPeaches;
import util.math.num.Vector;

/**
 * @author Simran
 */
public class GameWindow {

    private Scene scene;
    private Stage stage;
    private Engine engine;

    public GameWindow(Stage stage, Entity rootEntity){
        this.stage = stage;
        VoogaPeaches.setIsGaming(true);
        engine = new Engine(rootEntity, PanelController.GRID_SIZE, true);
        scene = new Scene(engine.getCameraView(new Vector(PanelController.CAMERA_INIT_X, PanelController.CAMERA_INIT_Y),
                new Vector(PanelController.CAMERA_INIT_X_SIZE, PanelController.CAMERA_INIT_Y_SIZE)));
        stage.setScene(scene);
        stage.show();
        engine.play();
    }

}