package engine.camera;

import engine.entities.Entity;
import engine.managers.EntityManager;
import javafx.scene.layout.StackPane;
import util.math.num.Vector;

/**
 * whole map for the game
 *
 * current implementation update image for every existing entity, even if it is outside the camera
 *
 * need better implementation that only update relevant entity image
 */
public class Map extends StackPane{
    private EntityManager myManager;

    public Map(EntityManager manager) {

        myManager = manager;
    }

    /**
     * update the map at each frame
     *
     * this update should only add and remove background and static stuff so that the minimap get updated(unimplemented)
     *
     * should be called by engine loop
     */
    public void update() {
        for (Entity each: myManager.getBGEntity()) {
            this.getChildren().add(each.getRender().getImage());
        }
        for (Entity each: myManager.getNonBGEntity()) {
            this.getChildren().add(each.getRender().getImage());
        }
    }

    /**
     * this update is specific for Camera class so that the map update the image for every entity inside viewport
     * @param center
     * @param size
     */
    public void localUpdate(Vector center, Vector size) {
        // todo: add helper function for determining whether an entity is inside the box
    }
}
