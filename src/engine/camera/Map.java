package engine.camera;

import engine.entities.Layer;
import engine.managers.EntityManager;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import util.math.num.Vector;

/**
 * whole map for the game
 *
 * current implementation update image for every existing entity, even if it is outside the camera
 *
 * need better implementation that only update relevant entity image
 *
 * @author Estelle
 */
public class Map extends StackPane implements ListChangeListener<Layer>{
    private static final int GRIDS = 50;
    private EntityManager myManager;
    private Group myBGList;
    private GridPane myGrid;

    public Map(EntityManager manager) {

        myManager = manager;

        myGrid = new GridPane();
        myGrid.getColumnConstraints().add(new ColumnConstraints(GRIDS));
        myGrid.getRowConstraints().add(new RowConstraints(GRIDS));
        myGrid.setGridLinesVisible(true);
        myGrid.setPrefWidth(1000);
        myGrid.setPrefHeight(1000);
        myBGList = myManager.getBGImageList();

        this.getChildren().add(myGrid);
        this.getChildren().add(myManager.getBGImageList());

        myManager.addLayerListener(this);

    }


    /**
     * this update is specific for Camera class so that the map update the image for every entity inside viewport
     * @param center
     * @param size
     */
    public void localUpdate(Vector center, Vector size) {

        myManager.displayUpdate(center, size);

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

    public void print() {
        System.out.println(myBGList.getChildren().size());
    }
}
