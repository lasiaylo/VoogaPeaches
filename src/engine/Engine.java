package engine;

import database.GameSaver;
import engine.camera.Camera;
import engine.collisions.HitBox;
import engine.entities.Entity;
import engine.events.CollisionEvent;
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
        entityManager.setCamera(camera);

        timeline = new Timeline(new KeyFrame(Duration.millis(FRAME_PERIOD), e -> loop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void loop() {
        tick.recursiveFire(entityManager.getCurrentLevel());
        Map<HitBox, Entity> hitBoxes = getHitBoxes(entityManager.getCurrentLevel(), new HashMap<>());
        checkCollisions(hitBoxes);
    }

    public void save(String name) {
        new GameSaver(name).saveGame(entityManager.getRoot());
    }

    public void load(String name) {

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

    private Map<HitBox, Entity> getHitBoxes(Entity root, Map<HitBox, Entity> hitBoxes) {
        root.getHitBoxes().forEach(e -> hitBoxes.put(e, root));
        root.getChildren().forEach(e -> getHitBoxes(e, hitBoxes));
        return hitBoxes;
    }

    private void checkCollisions(Map<HitBox, Entity> hitBoxes) {
        for(HitBox hitBox : hitBoxes.keySet()) {
            for(HitBox other : hitBoxes.keySet()) {
                if(hitBox != other && hitBox.intersects(other)) {
                    new CollisionEvent(hitBox, hitBoxes.get(hitBox)).fire(hitBoxes.get(other));
                    new CollisionEvent(other, hitBoxes.get(other)).fire(hitBoxes.get(hitBox));                }
            }
        }
    }
}
