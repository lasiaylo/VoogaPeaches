package engine.camera;

import engine.entities.EntityManager;
import javafx.scene.layout.StackPane;
import util.math.num.Vector;

public class Camera {
    private EntityManager myManager;
    private StackPane myView;

    public Camera(EntityManager manager) {
        myManager = manager;
        myView = new StackPane();
    }

    public StackPane getView() {
        return myView;
    }
    
}
