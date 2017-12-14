package authoring.buttons.strategies;

import authoring.PanelController;
import authoring.panels.reserved.CameraPanel;
import database.jsonhelpers.JSONToObjectConverter;
import engine.Engine;
import engine.EntityManager;
import engine.entities.Entity;
import org.json.JSONObject;

import java.util.Stack;

public class ResetStrategy implements IButtonStrategy {

    /* Instance Varables */
    private Engine engine;
    private CameraPanel cameraPanel;

    public ResetStrategy(PanelController controller, CameraPanel cameraPanel) {
        this.engine = controller.getEngine();
        this.cameraPanel = cameraPanel;
    }

    @Override
    public void fire() {
        engine.pause();
        JSONObject jsonObject = engine.getLastState();
        JSONToObjectConverter<Entity> converter = new JSONToObjectConverter<>(Entity.class);
        Entity resetRoot = converter.createObjectFromJSON(Entity.class, jsonObject);
        EntityManager manager = engine.getEntityManager();
        String levelName = manager.getCurrentLevelName();
        bottomUpInitialize(resetRoot);
        manager.setRoot(resetRoot);

        manager.changeLevel(levelName);
//
        cameraPanel.updateLevel();

    }

    private void bottomUpInitialize(Entity root) {
        if(root == null) return;
        for(Entity child : root.getChildren())
            bottomUpInitialize(child);
        root.initialize();
    }
}
