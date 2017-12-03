package engine;

import database.GameSaver;
import engine.camera.Camera;
import engine.entities.Entity;
import engine.events.KeyPressEvent;
import engine.events.TickEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import util.ErrorDisplay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Initiates the engine's loop with a root game entity
 *
 * @author Albert
 * @author estellehe
 */
public class Engine {
    private static final int MAX_FRAMES_PER_SECOND = 60;
    private static final int FRAME_PERIOD = 1000 / MAX_FRAMES_PER_SECOND;

    private Entity root;
    private Map<String, Entity> levels;
    private Entity currentLevel;
    private TickEvent tick = new TickEvent();
    private Timeline timeline;
    private Camera camera;

    /**
     * Creates a new Engine
     *
     * @param root      root game entity
     * @param level     name of the first level
     */
    public Engine(Entity root, String level, Camera camera) {
        this.root = root;
        this.camera = camera;
        this.levels = new HashMap<>();
        initializeLevelMap();
        changeLevel(level);

        for(String key : levels.keySet()) {
            Entity entity = levels.get(key);
            entity.getNodes().getScene().setOnKeyPressed(e -> new KeyPressEvent(e.getCode()).fire(entity));
        }

        timeline = new Timeline(new KeyFrame(Duration.millis(FRAME_PERIOD), e -> loop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
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
            camera = new Camera((currentLevel = this.levels.get(level)).getNodes());

    }

    private void loop() {
        tick.recursiveFire(currentLevel);
    }

    public void save(String name) {
        new GameSaver(name).saveTrackableObjects(root);
    }
}
