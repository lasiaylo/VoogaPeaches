package engine;

import database.GameSaver;
import database.jsonhelpers.JSONHelper;
import engine.camera.Camera;
import engine.collisions.HitBox;
import engine.entities.Entity;
import engine.events.TickEvent;
import engine.collisions.CollisionCheck;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.VoogaPeaches;
import org.json.JSONObject;
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

    private JSONObject lastState;
    private EntityManager entityManager;
    private TickEvent tick = new TickEvent(FRAME_PERIOD);
    private Timeline timeline;
    private Camera camera;
    private ScrollPane scrollPane;
    private boolean isGaming;
    private Entity root;

    /**
     * Creates a new Engine
     *
     * @param root  root game entity
     */
    public Engine(Entity root, int gridSize, boolean gaming) {
        this.isGaming = gaming;
        this.root = root;
        this.entityManager = new EntityManager(root, gridSize, gaming);
        this.camera = new Camera(entityManager.getCurrentLevel());
        entityManager.setCamera(camera);
        timeline = new Timeline(new KeyFrame(Duration.millis(FRAME_PERIOD), e -> loop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.lastState = JSONHelper.JSONForObject(root);
    }

    /**
     * recursive fires a tickevent down the tree of the current level. Checks collisions
     */
    private void loop() {
        tick.recursiveFire(entityManager.getCurrentLevel());
        Map<HitBox, Entity> hitBoxes = getHitBoxes(entityManager.getCurrentLevel(), new HashMap<>());
        CollisionCheck.checkCollisions(hitBoxes);
    }

    /**
     * Saves game
     * @param name  path to save to
     */
    public void save(String name) {
        new GameSaver(name).saveGame(entityManager.getRoot());
    }

    /**
     * @return  entity manager contained inside
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * plays the engine
     */
    public void play() {
        timeline.play();
        scrollPane.requestFocus();
        this.isGaming = true;
        lastState = JSONHelper.JSONForObject(root);
        VoogaPeaches.setIsGaming(isGaming);
        entityManager.setIsGaming(isGaming);
        camera.fixCamera();
        camera.requestFocus();
    }

    /**
     * pauses the engine
     */
    public void pause() {
        timeline.pause();
        this.isGaming = false;
        entityManager.setIsGaming(isGaming);
        VoogaPeaches.setIsGaming(isGaming);
        camera.freeCamera();
    }

    /**
     * @param center    center of view
     * @param size      size of window
     * @return          the view of the camera at @param center with size @param size
     */
    public ScrollPane getCameraView(Vector center, Vector size) {
        scrollPane = camera.getView(center,size);
        return scrollPane;
    }

    /**
     * @param size  size of minimap
     * @return      minimap with size @parma size
     */
    public Pane getMiniMap(Vector size) {
        return camera.getMinimap(size);
    }

    /**
     * @param root      root of tree to start search over
     * @param hitBoxes  map to add hitboxes to
     * @return          all hitboxes contained in the tree under an entity
     */
    private Map<HitBox, Entity> getHitBoxes(Entity root, Map<HitBox, Entity> hitBoxes) {
        root.getHitBoxes().forEach(e -> hitBoxes.put(e, root));
        root.getChildren().forEach(e -> getHitBoxes(e, hitBoxes));
        return hitBoxes;
    }

    public JSONObject getLastState() {
        return lastState;
    }
}
