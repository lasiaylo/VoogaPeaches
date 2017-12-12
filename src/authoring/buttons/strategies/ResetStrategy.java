package authoring.buttons.strategies;

import authoring.PanelController;
import authoring.panels.reserved.CameraPanel;
import database.jsonhelpers.JSONToObjectConverter;
import engine.Engine;
import engine.EntityManager;
import engine.camera.Camera;
import engine.entities.Entity;
import org.json.JSONObject;
import util.pubsub.PubSub;
import util.pubsub.messages.EntityManagerMessage;

import java.util.List;
import java.util.stream.Collectors;

public class ResetStrategy implements IButtonStrategy {
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
        resetRoot.recursiveInitialize();
        manager.setRoot(resetRoot);
        Entity currentLevel = manager.changeLevel(levelName);
        cameraPanel.clear(currentLevel.getChildren().size());
    }
}
