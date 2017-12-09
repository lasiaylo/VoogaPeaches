package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class EntityVisualizer {
    public static final double RADIUS = 50;
    public static final double CONNECTION_LENGTH = 30;
    public static final int MAX_DISPLAY = 1;

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
        text.setStyle(
                "-fx-font-family: \"Georgia\";" +
                        "-fx-font-size: 8px;"
        );
        group.getChildren().add(text);
    }

    private void drawRoot() {
        rootCircle = new Circle(RADIUS);
        rootCircle.setCenterX(0);
        rootCircle.setCenterY(0);
        rootCircle.setStroke(Color.BLACK);
        rootCircle.setFill(Color.WHITE);
        group.getChildren().add(rootCircle);
        createText(root.UIDforObject(), rootCircle);
    }

    private void drawChildren(Entity root) {
        root.getChildren().forEach(e -> children.add(new EntityVisualizer(gameVisualizer, e, this, root)));
        if (children.size() <= MAX_DISPLAY) {
            draw(children.size());
        } else {
            ChoiceBox<String> cb = new ChoiceBox<>();
            group.getChildren().add(cb);
            cb.setStyle("-fx-text-box-border: transparent;"
                    + "-fx-background-color: transparent, transparent, transparent, transparent;"
                    + "-fx-text-alignment: center;");
            Circle lastCircle = draw(MAX_DISPLAY);
            lastCircle.setFill(Color.BISQUE);
            cb.setLayoutX(lastCircle.getCenterX());
            cb.setLayoutY(lastCircle.getCenterY());
            cb.toFront();
            lastCircle.setOnMouseMoved(f -> {
                for (int i = MAX_DISPLAY; i < children.size(); i++){
                    String UID = children.get(i).root.UIDforObject();
                    if (!cb.getItems().contains(UID)){
                        cb.getItems().add(UID);
                    }
                }
                cb.setOnAction(g -> {
                    if (cb.getValue() != null) {
                        gameVisualizer.focus(children.get(indexByUID(cb.getValue())));
                        cb.valueProperty().set(null);
                    }
                });
            });
        }
    }

    private Circle draw(int size) {
        double angle = 2 * Math.PI / (size + 1);
        Circle c = new Circle();
        if (parent != null) {
            Circle parentCircle = drawTotal(0, vizParent);
            parentCircle.setFill(Color.CORNFLOWERBLUE);
        }
        for (int i = 0; i < size; i++) {
            c = drawTotal(angle * (i + 1), children.get(i));
            System.out.println(angle * (i + 1));
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

    private Vector vecFromHypotenuse(Vector ogPosition, double length, double angle) {
        return ogPosition.add(new Vector(length * Math.cos(angle), length * Math.sin(angle)));
    }

    protected Group getGroup() {
        return group;
    }

    private int indexByUID(String UID){
        for (int i = MAX_DISPLAY; i < children.size(); i++){
            if (children.get(i).root.UIDforObject().equals(UID)){
                return i;
            }
        }
        return -1;
    }

}