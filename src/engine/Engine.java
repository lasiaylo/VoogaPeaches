package engine;

import database.GameSaver;
import engine.camera.NewCamera;
import engine.collisions.HitBox;
import engine.entities.Entity;
import engine.events.CollisionEvent;
import engine.events.TickEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
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
    private NewCamera camera;
    private ScrollPane scrollPane;
    private boolean isGaming;

    /**
     * Creates a new Engine
     *
     * @param root  root game entity
     */
    public Engine(Entity root, int gridSize, boolean gaming) {
        this.isGaming = gaming;
        this.entityManager = new EntityManager(root, gridSize, gaming);
        this.camera = new NewCamera(entityManager.getCurrentLevel());
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

    public void load(Entity root, int gridSize, boolean gaming) {
        this.isGaming = gaming;
        this.entityManager = new EntityManager(root, gridSize, gaming);
        this.camera.changeLevel(entityManager.getCurrentLevel());
        entityManager.setCamera(this.camera);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void play() {
        timeline.play();
        scrollPane.requestFocus();
        this.isGaming = true;
        entityManager.setIsGaming(isGaming);
    }

    public void pause() {
        timeline.pause();
        this.isGaming = false;
        entityManager.setIsGaming(isGaming);
    }

    public EntityManager getManager() {
        return entityManager;
    }

    public Node getCameraView(Vector center, Vector size) {
        return camera.getView(center,size);
    }

    public Node getMiniMap(Vector size) {
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
