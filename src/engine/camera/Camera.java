package engine.camera;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import util.math.num.Vector;

/**
 * Camera that will pass a view to the authoring and player for game display
 *
 * do not extend scrollpane directly for the flexibility of adding more features like minimap
 *
 * @author Estelle He
 */
public class Camera {
    private ScrollPane myView;
    private Map myMap;
    private SubScene myMini;
    private Vector myCenter = new Vector(0, 0);
    // todo: set initial value in constructor
    private Vector mySize = new Vector(10, 10);

    public Camera(Map map) {
        myMap = map;
        myView = new ScrollPane(map);
        myView.setContent(map);
        myView.setPannable(false);
        myView.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myView.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**
     * set viewport to certain box and return scrollpane
     * @param center
     * @param size
     * @return myView
     */
    public ScrollPane getView(Vector center, Vector size) {
        myView.setViewportBounds(new BoundingBox(center.at(0)-size.at(0)/2, center.at(1)-size.at(1)/2, size.at(0), size.at(1)));
        myView.setPrefWidth(size.at(0));
        myView.setPrefHeight(size.at(1));
        hScroll((center.at(0) - size.at(0) / 2) / myView.getContent().getLayoutBounds().getWidth() - size.at(0));
        vScroll((center.at(1) - size.at(1) / 2) / myView.getContent().getLayoutBounds().getHeight() - size.at(1));


        myView.layout();
        myCenter = center;
        mySize = size;

        myView.setOnMouseClicked(e -> addBGblock(new Vector(e.getX(), e.getY())));

        return myView;
    }

    private void addBGblock(Vector pos) {
        myMap.addBGblock(pos);
    }

    private SubScene getMinimap(Vector size) {
        //need to check, just blind coding
        myMini = new SubScene(myMap, size.at(0), size.at(1));
        return myMini;
    }

    /**
     * update imageview inside the viewport
     */
    public void update() {
        myMap.localUpdate(myCenter, mySize);
    }


    private void vScroll(double num) {
        myView.setVmin(num);
        myView.setVmax(num);
        myView.setVvalue(num);
    }

    private void hScroll(double num) {
        myView.setHmax(num);
        myView.setHmin(num);
        myView.setHvalue(num);
    }

}
