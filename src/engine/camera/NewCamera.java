package engine.camera;

import engine.entities.Entity;
import engine.events.KeyPressEvent;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import util.math.num.Vector;

public class NewCamera {
    private SubScene scene;
    private Group group;
    private Camera camera;
    private Entity currentLevel;
    private Circle point;
    private Canvas miniMap;

    public NewCamera(Entity level){
        currentLevel = level;
        camera = new ParallelCamera();
        group = new Group();
        scene = new SubScene(group,0,0);
        scene.setCamera(camera);

        changeLevel(level);
    }

    public void changeLevel(Entity level) {
        if (levelIsEmpty())
            currentLevel.add(group);
        set(level.getNodes().getChildren().get(0));
        group.setOnKeyPressed(e->{
            new KeyPressEvent(e).recursiveFire(level);
            System.out.println("pressed");
        });
        currentLevel = level;
    }

    private void set(Node...node) {
        group.getChildren().clear();
        group.getChildren().addAll(node);
    }

    private boolean levelIsEmpty() {
        return currentLevel.getNodes().getChildren().size() == 0;
    }

    public Node getView(Vector position, Vector scale){
        scene.setWidth(scale.at(0));
        scene.setHeight(scale.at(1));
        moveCamera(position);
        return scene;
    }

    public Node getMinimap(Vector size){
        miniMap = new Canvas(size.at(0), size.at(1));
        miniMap.setStyle("-fx-border-color: black; -fx-border-width: 10");
        miniMap.getGraphicsContext2D().fillRect(0, 0, size.x, size.y);
        point = new Circle(camera.getLayoutX(),
               camera.getLayoutY(), 5, Color.RED);
        miniMap.setOnMouseClicked(this::moveCamera);

        Pane holder = new Pane(miniMap, point);
        holder.maxWidthProperty().bind(miniMap.widthProperty());
        holder.maxHeightProperty().bind(miniMap.heightProperty());
        return holder;
    }

    private void moveCamera(MouseEvent event){
        Vector vector = new Vector(event.getX(), event.getY());
        double xPos = group.getLayoutBounds().getWidth()/ miniMap.getWidth() * vector.at(0);
        double yPos = group.getLayoutBounds().getHeight()/ miniMap.getHeight() * vector.at(1);
        moveCamera(new Vector(xPos,yPos));
    }

    private void moveCamera(Vector position) {
        camera.setLayoutX(position.at(0));
        camera.setLayoutY(position.at(1));

        movePoint(position);
    }

    private void movePoint( Vector size){
        double xPos = miniMap.getWidth() / group.getLayoutBounds().getWidth() * size.at(0);
        double yPos = miniMap.getHeight() / group.getLayoutBounds().getHeight() *size.at(1);
        point.setCenterX(xPos);
        point.setCenterY(yPos);
        System.out.println(point.getCenterX());
    }


}
