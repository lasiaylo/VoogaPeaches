package authoring.GameWindow;

import authoring.PanelController;
import engine.Engine;
import engine.entities.Entity;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
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
    private Button start;
    private ScrollPane camera;

    public GameWindow(Stage stage, Entity rootEntity){
        this.stage = stage;
        this.start = new Button("Start");
        engine = new Engine(rootEntity, PanelController.GRID_SIZE, true);
        camera = engine.getCameraView(new Vector(PanelController.CAMERA_INIT_X, PanelController.CAMERA_INIT_Y).multiply(0.5),
                new Vector(PanelController.CAMERA_INIT_X_SIZE, PanelController.CAMERA_INIT_Y_SIZE));
        VBox root = new VBox(camera, start);
        root.setSpacing(10);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        start.setOnMouseClicked(e -> engine.play());
    }
}