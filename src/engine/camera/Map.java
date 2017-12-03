package engine.camera;

import engine.entities.Entity;
import engine.entities.Layer;
import engine.entities.Render;
import engine.managers.EntityManager;
import engine.util.FXProcessing;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
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
    private static final int MAXWIDTH = 5000;
    private EntityManager myManager;
    private Group myBGList;
    private Canvas myCanvas;
    private int myMode = 0;
    private Vector startPos = new Vector(0, 0);

    public Map(EntityManager manager) {

        myManager = manager;

        myCanvas = new Canvas(MAXWIDTH, MAXWIDTH);
        myBGList = myManager.getBGImageList();

        this.getChildren().add(myCanvas);
        this.getChildren().add(myBGList);
        this.setAlignment(myBGList, Pos.TOP_LEFT);
        myManager.addLayerListener(this);
        myCanvas.setOnMouseClicked(e -> addBGblock(new Vector(e.getX(), e.getY()), e));
        myCanvas.setOnMousePressed(e -> startDrag(e));
        myCanvas.setOnMouseReleased(e -> addBatch(e, startPos));
    }

    private void startDrag(MouseEvent event) {
        startPos = new Vector(event.getX(), event.getY());
        event.consume();
    }


    private void addBGblock(Vector pos, MouseEvent event) {
        Vector center = FXProcessing.getBGCenter(pos, GRIDS);
        myManager.addBG(center);
        event.consume();
    }

    private void addBatch(MouseEvent event, Vector start) {
        Vector end = new Vector(event.getX(), event.getY());
        Vector startC = FXProcessing.getBGCenter(start, GRIDS);
        Vector endC = FXProcessing.getBGCenter(end, GRIDS);
        for (double i = startC.at(0); i <= endC.at(0); i += GRIDS) {
            for (double j = startC.at(1); j <= endC.at(1); j += GRIDS) {
                System.out.println(new Vector(i, j));
                Vector center = FXProcessing.getBGCenter(new Vector(i, j), GRIDS);
                myManager.addBG(center);
            }
        }
        event.consume();
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
            this.getChildren().get(0).setOnDragDropped(e -> System.out.println("layer"));
        }
    }


}
