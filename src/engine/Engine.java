package engine;

import engine.camera.Camera;
import engine.entities.Entity;
import engine.events.TickEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import util.ErrorDisplay;

import java.util.HashMap;
import java.util.Map;

/**
 * Initiates the engine's loop with a root game entity
 * @author Albert
 * @author estellehe
 */
public class Engine {
    private static final int MAX_FRAMES_PER_SECOND = 60;
    public static final int FRAME_PERIOD = 1000 / MAX_FRAMES_PER_SECOND;

    private Entity root;
    private Map<String, Entity> levelMap;
    private Entity currentLevel;
    private TickEvent tick = new TickEvent();
    private Timeline myGamingTimeline;
    private Timeline myEngineTimeline;
    private Camera myCamera;

    /**
     * Creates a new Engine
     * @param root  root Entity of the game
     * @param level name of the first level
     */
    public Engine(Entity root, String level, Camera camera) {
        this.root = root;
        this.myCamera = camera;
        levelMap = new HashMap<>();
        try {
            root.getChildren().forEach(e -> levelMap.put((String) e.getProperty("name"), e));
        } catch (ClassCastException e) {
            ErrorDisplay castError = new ErrorDisplay("Level Name Property Does Not Exist");
            castError.displayError();
        }
        currentLevel = levelMap.get(level);

        myGamingTimeline = new Timeline(new KeyFrame(Duration.millis(FRAME_PERIOD), e -> loop()));
        myGamingTimeline.setCycleCount(Timeline.INDEFINITE);

//        myEngineTimeline = new Timeline(new KeyFrame(Duration.millis(FRAME_PERIOD), e -> myCamera.update()));
//        myEngineTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void loop() {
        tick.recursiveFire(currentLevel);
    }
}
