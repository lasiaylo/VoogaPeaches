package engine.camera;

import javafx.beans.binding.NumberBinding;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    private Vector myCenter = new Vector(0, 0);
    private Vector mySize = new Vector(10, 10);
    private Canvas myMiniMap;
    private Circle myPoint;

    public Camera(Map map) {
        myMap = map;
        myView = new ScrollPane(map);
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
        //myView.setViewportBounds(new BoundingBox(center.at(0)-size.at(0)/2, center.at(1)-size.at(1)/2, size.at(0), size.at(1)));
        myView.setPrefWidth(size.at(0));
        myView.setPrefHeight(size.at(1));
        hScroll((center.at(0) - size.at(0) / 2) / myView.getContent().getLayoutBounds().getWidth() - size.at(0));
        vScroll((center.at(1) - size.at(1) / 2) / myView.getContent().getLayoutBounds().getHeight() - size.at(1));


        myView.layout();
        myCenter = center;
        mySize = size;


        return myView;
    }



    /**
     * update imageview inside the viewport
     */
    public void update() {
        myMap.localUpdate(myCenter, mySize);
    }


    /**
     * this method currently is doing nothing
     *
     * later can be used to adjust the inital position of the viewport
     * @param num
     */
    private void vScroll(double num) {
        myView.setVmin(0);
        myView.setVmax(1);
        myView.setVvalue(0);
    }

    /**
     * this method currently is doing nothing
     *
     * later can be used to adjust the inital position of the viewport
     * @param num
     */
    private void hScroll(double num) {
        myView.setHmax(1);
        myView.setHmin(0);
        myView.setHvalue(0);
    }

    /**
     * get minimap
     * @param size
     * @return
     */
    public Pane getMiniMap(Vector size) {
        myMiniMap = new Canvas(size.at(0), size.at(1));
        myMiniMap.getGraphicsContext2D().setFill(Color.BLACK);
        myMiniMap.getGraphicsContext2D().fillRect(0, 0, size.at(0), size.at(1));
        myPoint = new Circle(myView.getHvalue(), myView.getVvalue(), 5, Color.RED);

        NumberBinding xPoint = myView.hvalueProperty().multiply(size.at(0)).add(size.at(0));
        NumberBinding yPoint = myView.vvalueProperty().multiply(size.at(1)).add(size.at(1));
        myPoint.centerXProperty().bind(xPoint);
        myPoint.centerYProperty().bind(yPoint);

        Pane miniPane = new Pane();
        miniPane.getChildren().add(myMiniMap);
        miniPane.getChildren().add(myPoint);
        miniPane.setBackground(new Background(new BackgroundFill(Color.PINK, null, null)));
        myMiniMap.setOnMouseClicked(e -> moveCamera(e));

        return miniPane;

    }

    private void moveCamera(MouseEvent event) {
        myPoint.centerXProperty().unbind();
        myPoint.centerYProperty().unbind();

        myPoint.setCenterX(event.getX());
        myPoint.setCenterY(event.getY());
        myView.setHvalue(event.getX()/myMiniMap.getWidth());
        myView.setVvalue(event.getY()/myMiniMap.getHeight());

        NumberBinding xPoint = myView.hvalueProperty().multiply(myMiniMap.getWidth());
        NumberBinding yPoint = myView.vvalueProperty().multiply(myMiniMap.getHeight());
        myPoint.centerXProperty().bind(xPoint);
        myPoint.centerYProperty().bind(yPoint);

        event.consume();
    }




}
