package engine;

import engine.camera.Camera;
import engine.entities.Entity;
import engine.events.TickEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import util.ErrorDisplay;

import java.util.Map;

/**
 * Initiates the engine's loop with a root game entity
 *
 * @author Albert
 * @author estellehe
 */
public class Engine {
    private static final int MAX_FRAMES_PER_SECOND = 60;
    public static final int FRAME_PERIOD = 1000 / MAX_FRAMES_PER_SECOND;

    private Map<String, Entity> levels;
    private Entity currentLevel;
    private TickEvent tick = new TickEvent();
    private Timeline timeline;
    private Camera camera;

    /**
     * Creates a new Engine
     *
     * @param levels map of levels
     * @param level  name of the first level
     */
    public Engine(Map<String, Entity> levels, String level, Camera camera) {
        this.camera = camera;
        this.levels = levels;
        changeLevel(level);

        timeline = new Timeline(new KeyFrame(Duration.millis(FRAME_PERIOD), e -> loop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
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
}
