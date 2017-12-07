package engine.camera;

import engine.entities.Entity;
import javafx.beans.binding.NumberBinding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.math.num.Vector;

/**
 * Camera that will pass a view to the authoring and player for game display
 * <p>
 * do not extend scrollpane directly for the flexibility of adding more features like minimap
 *
 * @author Estelle He
 */
public class Camera {
    private ScrollPane view;
    private Entity currentLevel;
    private Canvas miniMap;
    private Vector center;
    private Vector scale;
    private Circle point;

    public Camera(Entity level) {
        currentLevel = level;

        view = new ScrollPane(level.getNodes().getChildren().get(0));
        view.setPannable(false);

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
        view.setPrefWidth(size.at(0));
        view.setPrefHeight(size.at(1));
        hScroll((center.at(0) - size.at(0) / 2) / view.getContent().getLayoutBounds().getWidth() - size.at(0));
        vScroll((center.at(1) - size.at(1) / 2) / view.getContent().getLayoutBounds().getHeight() - size.at(1));

        view.layout();
        this.center = center;
        this.scale = size;

        return view;
    }

    public void changeLevel(Entity level) {
        view.setContent(level.getNodes().getChildren().get(0));
        currentLevel = level;
    }

    public Pane getMinimap(Vector size) {
        miniMap = new Canvas(size.at(0), size.at(1));
        GraphicsContext gc = miniMap.getGraphicsContext2D();
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, size.at(0), size.at(1));
        //miniMap.setStyle("-fx-border-color: black; -fx-border-width: 10");
        point = new Circle(view.getHvalue(), view.getVvalue(), 5, Color.RED);

        NumberBinding xPoint = view.hvalueProperty().multiply(size.at(0));
        NumberBinding yPoint = view.vvalueProperty().multiply(size.at(1));
        point.centerXProperty().bind(xPoint);
        point.centerYProperty().bind(yPoint);

        Pane miniPane = new Pane();
        miniPane.getChildren().add(miniMap);
        miniPane.getChildren().add(point);

        miniMap.setOnMouseClicked(e -> moveCamera(e));

        return miniPane;

    }

    private void moveCamera(MouseEvent event) {
        point.centerXProperty().unbind();
        point.centerYProperty().unbind();

        point.setCenterX(event.getX());
        point.setCenterY(event.getY());
        view.setHvalue(event.getX()/miniMap.getWidth());
        view.setVvalue(event.getY()/miniMap.getHeight());

        NumberBinding xPoint = view.hvalueProperty().multiply(miniMap.getWidth());
        NumberBinding yPoint = view.vvalueProperty().multiply(miniMap.getHeight());
        point.centerXProperty().bind(xPoint);
        point.centerYProperty().bind(yPoint);

        event.consume();
    }



    private void vScroll(double num) {
        view.setVmin(0);
        view.setVmax(1);
        view.setVvalue(num);
    }

    private void hScroll(double num) {
        view.setHmax(1);
        view.setHmin(0);
        view.setHvalue(num);
    }
}
