package authoring.buttons.strategies;

import authoring.PanelController;
import authoring.panels.reserved.CameraPanel;
import database.jsonhelpers.JSONToObjectConverter;
import engine.Engine;
import engine.EntityManager;
import engine.entities.Entity;
import engine.events.ResetEvent;
import org.json.JSONObject;

/**
 * A strategy that resets the engine to a previous state specified by the last state json object within the engine
 * @author Albert
 */
public class ResetStrategy implements IButtonStrategy {

    /* Instance Varables */
    private Engine engine;
    private CameraPanel cameraPanel;

    /**
     * Creates a new ResetStrategy
     * @param controller    PanelController that contains the Engine to reset
     * @param cameraPanel   CameraPanel displaying the engine's current level
     */
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
        Entity oldRoot = manager.getRoot();
        new ResetEvent().recursiveFire(oldRoot);
        manager.setRoot(resetRoot);
        manager.changeLevel(levelName);
        cameraPanel.updateLevel();
    }

    /**
     * initializes the root tree from the bottom up
     * @param root  Entity to initialize
     */
    private void bottomUpInitialize(Entity root) {
        if(root == null) return;
        for(Entity child : root.getChildren())
            bottomUpInitialize(child);
        root.initialize();
    }
}
