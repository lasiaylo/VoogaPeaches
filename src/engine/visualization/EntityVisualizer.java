package engine.visualization;

import database.firebase.TrackableObject;
import engine.entities.Entity;
import javafx.scene.Group;
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
    public static final int MAX_DISPLAY = 4;

    private Group group;
    private Circle circle;
    private StackPane rootStackPane;
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

    private void drawRoot() {
        circle = new Circle(RADIUS);
        circle.setCenterX(0);
        circle.setCenterY(0);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        group.getChildren().add(circle);
        createText(root.UIDforObject(), circle);
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

    private void drawChildren(Entity root) {
        root.getChildren().forEach(e -> children.add(new EntityVisualizer(gameVisualizer, e, this, root)));
        if (children.size() <= MAX_DISPLAY) {
            draw(children.size());
        } else {
            Circle lastCircle = draw(MAX_DISPLAY);
            // replace last circle
        }
    }

    protected Group getGroup() {
        return group;
    }

    private Circle draw(int size) {
        double angle = 2 * Math.PI / (size + 1);
        Circle c = new Circle();
        if (parent != null) {
            drawTotal(0, vizParent);
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
        Vector radialOffset = vecFromHypotenuse(new Vector(circle.getCenterX(), circle.getCenterY()), circle.getRadius(), angle);
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
}