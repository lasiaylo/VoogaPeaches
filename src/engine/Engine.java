package engine;

import database.GameSaver;
import engine.camera.Camera;
import engine.collisions.HitBox;
import engine.entities.Entity;
import engine.events.TickEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import util.math.num.Vector;

import java.util.HashMap;
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
    private TickEvent tick = new TickEvent(FRAME_PERIOD);
    private Timeline timeline;
    private Camera camera;

    /**
     * Creates a new Engine
     *
     * @param root  root game entity
     */
    public Engine(Entity root, int gridSize) {
        this.entityManager = new EntityManager(root, gridSize);
        this.camera = new Camera(entityManager.getCurrentLevel());

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

    public void play() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

    public EntityManager getManager() {
        return entityManager;
    }

    public ScrollPane getCameraView(Vector center, Vector size) {
        return camera.getView(center, size);
    }

    public Pane getMiniMap(Vector size) {
        return camera.getMinimap(size);
    }

//    private Map<HitBox, Entity> getHitBoxes(Entity root, Map<HitBox, Entity> hitBoxes) {
//        root.getHitBoxes().forEach(e -> hitBoxes.put(e, root));
//        return
//    }
}
