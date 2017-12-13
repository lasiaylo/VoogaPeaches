package engine.camera;

import engine.entities.Entity;
import engine.events.KeyPressEvent;
import javafx.beans.binding.NumberBinding;
import javafx.scene.Group;
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
        view = new ScrollPane(level
                .getNodes());
//       if (currentLevel.getNodes().getChildren().size() == 0) {
//            currentLevel.add(view.getContent());
//        }
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
//        if (currentLevel.getNodes().getChildren().size() == 0) {
//            System.out.println(currentLevel.getNodes().getChildren().size());
//        }
        System.out.println("changing level in camera");
        view.setContent(new Group());
        view.setContent(level.getNodes());
        view.getContent().requestFocus();
        view.getContent().setOnKeyPressed(e -> new KeyPressEvent(e).recursiveFire(level));
        currentLevel = level;
        System.out.println("changed level in camera");
    }

    public Pane getMinimap(Vector size) {
        miniMap = new Canvas(size.at(0), size.at(1));
        miniMap.setStyle("-fx-border-color: black; -fx-border-width: 10");
        miniMap.getGraphicsContext2D().fillRect(0, 0, size.x, size.y);
        point = new Circle(view.getHvalue(), view.getVvalue(), 5, Color.RED);


        NumberBinding xPoint = view.hvalueProperty().multiply(size.at(0));
        NumberBinding yPoint = view.vvalueProperty().multiply(size.at(1));
        point.centerXProperty().bind(xPoint);
        point.centerYProperty().bind(yPoint);
        miniMap.setOnMouseClicked(this::moveCamera);

        Pane holder = new Pane(miniMap, point);
        holder.maxWidthProperty().bind(miniMap.widthProperty());
        holder.maxHeightProperty().bind(miniMap.heightProperty());
        return holder;
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
