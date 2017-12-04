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

    private EntityManager entityManager;
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
       // this.entityManager = new EntityManager(root, level);
        this.camera = camera;

        timeline = new Timeline(new KeyFrame(Duration.millis(FRAME_PERIOD), e -> loop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void loop() {
        tick.recursiveFire(entityManager.getCurrentLevel());
    }

    public void save(String name) {
        new GameSaver(name).saveTrackableObjects(entityManager.getRoot());
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
