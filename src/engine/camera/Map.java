package engine.camera;

import engine.entities.Layer;
import engine.managers.EntityManager;
import engine.util.FXProcessing;
import javafx.collections.ListChangeListener;
import javafx.geometry.BoundingBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.math.num.Vector;

import java.awt.*;

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
    private static final int MAXWIDTH = 5000;
    private EntityManager myManager;
    private Group myBGList;
    private Canvas myCanvas;

    public Map(EntityManager manager) {

        myManager = manager;

        myCanvas = new Canvas(MAXWIDTH, MAXWIDTH);
        myBGList = myManager.getBGImageList();

        this.getChildren().add(myCanvas);
        this.getChildren().add(myBGList);
        this.setAlignment(myBGList, Pos.TOP_LEFT);
        myManager.addLayerListener(this);
        myCanvas.setOnMouseClicked(e -> addBGblock(new Vector(e.getX(), e.getY())));

    }


    public void addBGblock(Vector pos) {
        Vector center = FXProcessing.getBGCenter(pos, GRIDS);
        myManager.addBG(center);
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
        while (c.next()) {
            for (Layer each : c.getAddedSubList()) {
                this.getChildren().add(each.getImageList());
                this.setAlignment(each.getImageList(), Pos.TOP_LEFT);
            }
        }
    }

}
