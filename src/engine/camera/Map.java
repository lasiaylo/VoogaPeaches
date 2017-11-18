package engine.camera;

import engine.entities.Entity;
import engine.entities.Layer;
import engine.managers.EntityManager;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.StackPane;
import util.math.num.Vector;

/**
 * whole map for the game
 *
 * current implementation update image for every existing entity, even if it is outside the camera
 *
 * need better implementation that only update relevant entity image
 */
public class Map extends StackPane implements ListChangeListener<Layer>{
    private EntityManager myManager;
    public Map(EntityManager manager) {

        myManager = manager;

        this.getChildren().add(myManager.getBGImageList());

        myManager.addLayerListener(this);

    }


    /**
     * this update is specific for Camera class so that the map update the image for every entity inside viewport
     * @param center
     * @param size
     */
    public void localUpdate(Vector center, Vector size) {
        // todo: add helper function for determining whether an entity is inside the box
    }

    /**
     * whenever a layer is added to the manager, map would be notified to add a new group/layer of imageview
     * @param c
     */
    @Override
    public void onChanged(Change<? extends Layer> c) {
        for (Layer each: c.getAddedSubList()) {
            this.getChildren().add(each.getImageList());
        }
    }
}
