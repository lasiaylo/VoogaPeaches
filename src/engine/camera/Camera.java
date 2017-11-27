package engine.camera;

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
        myView.setPrefWidth(size.at(0));
        myView.setPrefHeight(size.at(1));
        myView.setHvalue(0);//TODO fix this
        myView.setVvalue(0);

        myView.layout();
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

    public void print() {
        myMap.print();
    }

}
