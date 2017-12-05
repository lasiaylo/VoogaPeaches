package engine.camera;

import engine.entities.Entity;
import javafx.geometry.BoundingBox;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import util.math.num.Vector;

import java.util.Iterator;

/**
 * Camera that will pass a view to the authoring and player for game display
 * <p>
 * do not extend scrollpane directly for the flexibility of adding more features like minimap
 *
 * @author Estelle He
 */
public class Camera {
    private ScrollPane view;
    private Group node;
    private SubScene mini;
    private Vector center;
    private Vector scale;

    public Camera(Group level) {
        this.node = level;
        view = new ScrollPane(node);
        view.setPannable(false);
        view.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        view.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        center = new Vector(0, 0);
        scale = new Vector(10, 10);
    }

    /**
     * set viewport to certain box and return scrollpane
     *
     * @param center
     * @param size
     * @return view
     */
    public ScrollPane getView(Vector center, Vector size) {
        view.setViewportBounds(new BoundingBox(center.at(0) - size.at(0) / 2, center.at(1) - size.at(1) / 2, size.at(0), size.at(1)));
        view.setPrefWidth(size.at(0));
        view.setPrefHeight(size.at(1));
        hScroll((center.at(0) - size.at(0) / 2) / view.getContent().getLayoutBounds().getWidth() - size.at(0));
        vScroll((center.at(1) - size.at(1) / 2) / view.getContent().getLayoutBounds().getHeight() - size.at(1));


        view.layout();
        this.center = center;
        this.scale = size;

        return view;
    }

    public void setView(Entity entity) {
        //todo
    }

    private SubScene getMinimap(Vector size) {
        //todo, get from old code
    }

    /**
     * update imageview inside the viewport
     */
    public void update() {
        node.setLayoutX(center.x);
        node.setLayoutY(center.y);
        node.setScaleX(scale.x);
        node.setScaleY(scale.y);
    }


    private void vScroll(double num) {
        view.setVmin(num);
        view.setVmax(num);
        view.setVvalue(num);
    }

    private void hScroll(double num) {
        view.setHmax(num);
        view.setHmin(num);
        view.setHvalue(num);
    }
}
