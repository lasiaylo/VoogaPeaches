package engine;

import engine.camera.Camera;
import engine.entities.Entity;
import engine.events.KeyPressEvent;
import util.ErrorDisplay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntityManager {
    private Entity root;
    private Map<String, Entity> levels;
    private Entity currentLevel;
    private Camera camera;


    public EntityManager(Entity root, String currentLevel, Camera camera) {
        this.root = root;
        this.levels = new HashMap<>();
        this.camera = camera;

        initializeLevelMap();
        this.currentLevel = levels.get(currentLevel);
        for(String key : levels.keySet()) {
            Entity entity = levels.get(key);
            entity.getNodes().getScene().setOnKeyPressed(e -> new KeyPressEvent(e.getCode()).fire(entity));
        }
    }

    private void initializeLevelMap() {
        try {
            Iterator<Entity> children = root.getChildren();
            while(children.hasNext()) {
                Entity child = children.next();
                levels.put((String) child.getProperty("name"), child);
            }
        } catch(ClassCastException e) {
            ErrorDisplay eDisplay = new ErrorDisplay("Fuck you", "Name was not string");
            eDisplay.displayError();
        }
    }

    /**
     * Change current level
     *
     * @param level: new level
     */
    public void changeLevel(String level) {
        if (!levels.containsKey(null))
            new ErrorDisplay("Fuck you!", "Level " + level + " does not exist");
        else
            camera.setView((currentLevel = this.levels.get(level)));
//            camera = new Camera((currentLevel = this.levels.get(level)).getNodes());
    }



    public Entity getCurrentLevel() {
        return currentLevel;
    }

    public Entity getRoot() {
        return root;
    }
}
