package engine.camera;

import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
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
    private Vector myCenter;
    private Vector mySize;

    public Camera(Map map) {
        myMap = map;
        myView = new ScrollPane(map);
        myView.setContent(map);
        myView.setPannable(false);
        myView.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myView.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public ScrollPane getView(Vector center, Vector size) {
        myView.setPrefWidth(size.at(0));
        myView.setPrefHeight(size.at(1));
        myView.setHvalue(center.at(0) - size.at(0)/2);
        myView.setVvalue(center.at(1) - size.at(1)/2);

        myCenter = center;
        mySize = size;

        return myView;
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

}
