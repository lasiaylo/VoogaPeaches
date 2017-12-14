package engine.camera;

import engine.entities.Entity;
import engine.events.KeyPressEvent;
import engine.events.MousePressedEvent;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
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
 * @author Kelly Zhang
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

        changeLevel(level);
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
//        hScroll((center.at(0) - size.at(0) / 2) / view.getContent().getLayoutBounds().getWidth() - size.at(0));
//        vScroll((center.at(1) - size.at(1) / 2) / view.getContent().getLayoutBounds().getHeight() - size.at(1));
        hScroll(0);
        vScroll(0);

        view.layout();
        this.center = center;
        this.scale = size;

        return view;
    }

    public void changeLevel(Entity level) {
        if (currentLevel.getNodes().getChildren().size() == 0) {
            currentLevel.add(view.getContent());
        }
        view.setContent(level.getNodes().getChildren().get(0));
        view.getContent().requestFocus();
        view.getContent().setOnKeyPressed(e -> new KeyPressEvent(e).recursiveFire(level));
        currentLevel = level;
    }

    public Pane getMinimap(Vector size) {
        miniMap = new Canvas(size.at(0), size.at(1));
        miniMap.setStyle("-fx-border-color: black; -fx-border-width: 10");
        miniMap.getGraphicsContext2D().fillRect(0, 0, size.x, size.y);
        point = new Circle(view.getHvalue(), view.getVvalue(), 5, Color.RED);

        point.setOnMousePressed(circleOnMousePressedEventHandler);
        point.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        NumberBinding xPoint = view.hvalueProperty().multiply(size.at(0));
        NumberBinding yPoint = view.vvalueProperty().multiply(size.at(1));
        point.centerXProperty().bind(xPoint);
        point.centerYProperty().bind(yPoint);
        miniMap.setOnMouseClicked(circleOnMouseClickedEventHandler);

        Pane holder = new Pane(miniMap, point);
        holder.maxWidthProperty().bind(miniMap.widthProperty());
        holder.maxHeightProperty().bind(miniMap.heightProperty());
        return holder;
    }

    EventHandler<MouseEvent> circleOnMouseClickedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    point.centerXProperty().unbind();
                    point.centerYProperty().unbind();

                    point.setCenterX(t.getX());
                    point.setCenterY(t.getY());
                    view.setHvalue(t.getX()/miniMap.getWidth());
                    view.setVvalue(t.getY()/miniMap.getHeight());

                    NumberBinding xPoint = view.hvalueProperty().multiply(miniMap.getWidth());
                    NumberBinding yPoint = view.vvalueProperty().multiply(miniMap.getHeight());
                    point.centerXProperty().bind(xPoint);
                    point.centerYProperty().bind(yPoint);
                }
            };

    private double translateX;
    private double translateY;
    private double orgX;
    private double orgY;
    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    point.centerXProperty().unbind();
                    point.centerYProperty().unbind();

                    orgX = t.getSceneX();
                    orgY = t.getScreenY();
                    translateX = ((Circle) (t.getSource())).getTranslateX();
                    translateY = ((Circle) (t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgX;
                    double offsetY = t.getSceneY() - orgY;

                    double newTranslateX = translateX + offsetX;
                    double newTranslateY = translateY + offsetY;

                    view.setHvalue(newTranslateX/miniMap.getWidth());
                    view.setVvalue(newTranslateY/miniMap.getHeight());

                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);

                    NumberBinding xPoint = view.hvalueProperty().multiply(miniMap.getWidth());
                    NumberBinding yPoint = view.vvalueProperty().multiply(miniMap.getHeight());
                    point.centerXProperty().bind(xPoint);
                    point.centerYProperty().bind(yPoint);
                }
            };

    private void vScroll(double num) {
        view.setVmin(num);
        view.setVmax(num);
        view.setVvalue(num);
        view.vminProperty().bind(view.vvalueProperty());
        view.vmaxProperty().bind(view.vvalueProperty());
    }

    private void hScroll(double num) {
        view.setHmax(num);
        view.setHmin(num);
        view.setHvalue(num);
        view.hminProperty().bind(view.hvalueProperty());
        view.hmaxProperty().bind(view.hvalueProperty());
    }
}
