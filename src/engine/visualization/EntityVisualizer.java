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
    public static final double RADIUS = 20;
    public static final double CONNECTION_LENGTH = 75;
    public static final int MAX_DISPLAY = 4;

    private Group group;
    private Circle rootCircle;
    private List<EntityVisualizer> children;
    private List<Line> connections = new ArrayList<>();
    private Entity root;
    private GameVisualizer gameVisualizer;
    private Entity parent;
    private EntityVisualizer vizParent;

    public EntityVisualizer(GameVisualizer gameVisualizer, Entity root, EntityVisualizer vizParent, Entity parent, double rootPosX, double rootPosY) {
        this.root = root;
        this.parent = parent;
        this.vizParent = vizParent;
        this.gameVisualizer = gameVisualizer;
        children = new ArrayList<>();
        group = new Group();
        rootCircle = new Circle(RADIUS);
        rootCircle.setCenterX(rootPosX);
        rootCircle.setCenterY(rootPosY);
        group.getChildren().add(rootCircle);

        //rootCircle.setCenterX(rootPosX);
        //rootCircle.setCenterY(rootPosY);

        //drawRoot(rootPosX, rootPosY);
        //drawChildren(root);
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

    private void drawTree(EntityVisualizer entVis){
        if (entVis.root.getChildren().isEmpty()){
            return;
        }
        else {
            double angle = 2 * Math.PI / (entVis.root.getChildren().size() + 1);
            for (int j = 0; j < entVis.root.getChildren().size(); j++) {
                Line l = drawLine(angle * (j + 1), entVis.rootCircle);
                entVis.children.add(new EntityVisualizer(gameVisualizer, entVis.root.getChildren().get(j), this, entVis.root, l.getEndX(), l.getEndY()));
                drawCircle(angle, entVis.children.get(j), l.getEndX(), l.getEndY());
                drawTree(entVis.children.get(j));
            }
        }
    }

    public void drawRoot() {
        rootCircle.setStroke(Color.BLACK);
        rootCircle.setFill(Color.WHITE);
        //group.getChildren().add(rootCircle);
        createText(root.UIDforObject(), rootCircle);
        if (!root.getChildren().isEmpty()){
            drawTree(this);
        }
    }

    private void drawChildren(Entity root) {
        root.getChildren().forEach(e -> {
           //children.add(new EntityVisualizer(gameVisualizer, e, this, root));
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
            Circle parentCircle = drawTotal(0, vizParent, rootCircle);
            parentCircle.setFill(Color.CORNFLOWERBLUE);
        }
        for (int i = 0; i < size; i++) {
            c = drawTotal(angle * (i + 1), children.get(i), children.get(i).rootCircle);
            System.out.println(angle * (i + 1));
        }
        return c;
    }

    private Circle drawTotal(double angle, EntityVisualizer entityVisualizer, Circle fromCircle) {
        Line line = drawLine(angle, fromCircle);
        return drawCircle(angle, entityVisualizer, line.getEndX(), line.getEndY());
    }

    private Line drawLine(double angle, Circle fromCircle) {
        Vector radialOffset = vecFromHypotenuse(new Vector(fromCircle.getCenterX(), fromCircle.getCenterY()), rootCircle.getRadius(), angle);
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