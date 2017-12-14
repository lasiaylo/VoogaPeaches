package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class EntityVisualizer {

    private static final double RADIUS = 50;
    private static final double CONNECTION_LENGTH = 30;
    private static final int MAX_DISPLAY = 4;

    private Group group;
    private Circle rootCircle;
    private List<EntityVisualizer> children;
    private List<Line> connections = new ArrayList<>();
    private Entity root;
    private GameVisualizer gameVisualizer;
    private Entity parent;
    private EntityVisualizer vizParent;

    public EntityVisualizer(GameVisualizer gameVisualizer, Entity root, EntityVisualizer vizParent, Entity parent) {
        this.root = root;
        this.parent = parent;
        this.vizParent = vizParent;
        this.gameVisualizer = gameVisualizer;
        children = new ArrayList<>();
        group = new Group();
        drawRoot();
        drawChildren(root);
    }

    private void createText(String s, Circle c) {
        Text text = new Text(c.getCenterX(), c.getCenterY(), s.substring(0, 5));
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle("-fx-font-family: \"Georgia\";" + "-fx-font-size: 8px;");
        group.getChildren().add(text);
    }

    private void drawRoot() {
        rootCircle = new Circle(0, 0, RADIUS);
        rootCircle.setStroke(Color.BLACK);
        rootCircle.setFill(Color.WHITE);
        group.getChildren().add(rootCircle);
        createText(root.UIDforObject(), rootCircle);
    }

    private void drawChildren(Entity root) {
        root.getChildren().forEach(e -> {
            children.add(new EntityVisualizer(gameVisualizer, e, this, root));
            drawChildren(e);
        });
        if (children.size() <= MAX_DISPLAY) {
            draw(children.size());
        } else {
            Circle lastCircle = draw(MAX_DISPLAY);
            lastCircle.setFill(Color.BISQUE);
            ChoiceBox<String> cb = new ChoiceBox<>();
            cb.setVisible(false);
            cb.setLayoutX(lastCircle.getCenterX());
            cb.setLayoutY(lastCircle.getCenterY());
            group.getChildren().add(cb);
            cb.toFront();
            lastCircle.setOnMouseClicked(f -> {
                for (int i = MAX_DISPLAY - 1; i < children.size(); i++){
                    String UID = children.get(i).root.UIDforObject();
                    if (!cb.getItems().contains(UID)){
                        cb.getItems().add(UID);
                    }
                }
                cb.show();
                cb.setOnAction(g -> {
                    if (cb.getValue() != null) {
                        gameVisualizer.focus(children.get(indexByUID(cb.getValue(), this)));
                        cb.valueProperty().set(null);
                    }
                });
            });
        }
    }

    private Circle draw(int size) {
        Circle c = new Circle();
        if (parent != null) {
            Circle parentCircle = drawTotal(0, vizParent);
            parentCircle.setFill(Color.CORNFLOWERBLUE);
        }
        for (int i = 0; i < size; i++) {
            c = drawTotal((2 * Math.PI)/(MAX_DISPLAY)*i, children.get(i));
        }
        return c;
    }

    private Circle drawTotal(double angle, EntityVisualizer entityVisualizer) {
        Line line = drawLine(angle);
        return drawCircle(angle, entityVisualizer, line.getEndX(), line.getEndY());
    }

    private Line drawLine(double angle) {
        Vector radialOffset = vecFromHypotenuse(new Vector(rootCircle.getCenterX(), rootCircle.getCenterY()), rootCircle.getRadius(), angle);
        Vector lineEnd = vecFromHypotenuse(radialOffset, CONNECTION_LENGTH, angle);
        Line line = new Line(radialOffset.at(0), radialOffset.at(1), lineEnd.at(0), lineEnd.at(1));
        connections.add(line);
        group.getChildren().add(line);
        return line;
    }

    private Circle drawCircle(double angle, EntityVisualizer entityVisualizer, double lineEndX, double lineEndY) {
        Circle newCircle = new Circle(RADIUS);
        newCircle.setCenterX(lineEndX + RADIUS * Math.cos(angle));
        newCircle.setCenterY(lineEndY + RADIUS * Math.sin(angle));
        newCircle.setFill(Color.ORCHID);
        group.getChildren().add(newCircle);
        createText(entityVisualizer.root.UIDforObject(), newCircle);
        newCircle.setOnMouseClicked(e -> gameVisualizer.focus(entityVisualizer));
        return newCircle;
    }

    private Vector vecFromHypotenuse(Vector oldPosition, double length, double angle) {
        return oldPosition.add(new Vector(length * Math.cos(angle), length * Math.sin(angle)));
    }

    protected Group getGroup() { return group; }

    private int indexByUID(String UID, EntityVisualizer e){
        for (int i = MAX_DISPLAY; i < e.children.size(); i++){
            if (e.children.get(i).root.UIDforObject().equals(UID)){
                return i;
            }
        }
        return -1;
    }
}