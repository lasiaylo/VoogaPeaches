package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import util.math.num.Vector;
import java.util.ArrayList;
import java.util.List;

public class EntityVisualizer {

    private static final double LENGTH = 25;
    private static final int MAX_DISPLAY = 4;
    private static final double PARENT_ANGLE = (3 * Math.PI)/2;
    private static final double PARENT_LENGTH = 150;
    private static final double RADIUS = 50;

    private Group group;
    private List<EntityVisualizer> children;
    private List<Line> connections = new ArrayList<>();
    private GameVisualizer gameVisualizer;
    private EntityVisualizer parentVisualizer;
    private Entity root;
    private Entity parent;
    private Circle rootCircle;

    public EntityVisualizer(GameVisualizer gameVisualizer, EntityVisualizer parentVisualizer, Entity root) {
        this.root = root;
        this.parentVisualizer = parentVisualizer;
        this.gameVisualizer = gameVisualizer;
        children = new ArrayList<>();
        group = new Group();
        addChildren();
        drawRoot();
        if (parentVisualizer != null){
            this.parent = parentVisualizer.root;
            drawParent();
        }
        drawChildren();
    }

    private void addChildren(){
        root.getChildren().forEach(e -> {
            children.add(new EntityVisualizer(gameVisualizer, this, e));
        });
    }

    private void drawRoot() {
        rootCircle = new Circle(0, 0, RADIUS);
//        rootCircle.setStroke(Color.BLACK);
//        rootCircle.setFill(Color.WHITE);
        Stop[] stops = new Stop[] { new Stop(0.0f, Color.NAVY), new Stop(0.5f, Color.MEDIUMSLATEBLUE), new Stop(1.0f, Color.PLUM)};
        RadialGradient rg = new RadialGradient(90, 0.8, 0.5, 0.5, 1.0, true, CycleMethod.NO_CYCLE, stops);
        rootCircle.setFill(rg);
        group.getChildren().add(rootCircle);
        createText(root.UIDforObject(), rootCircle);
    }

    private void drawParent() {
        Circle parentCircle = drawTotal(PARENT_ANGLE, PARENT_LENGTH, this.parentVisualizer);
        parentCircle.setFill(Color.CORNFLOWERBLUE);
    }

    private void drawChildren() {
        if (children.size() <= MAX_DISPLAY) {draw(children.size());}
        else {
            Circle lastCircle = restyleLastCircle(draw(MAX_DISPLAY));
            ChoiceBox<String> cb = createChoiceBox(lastCircle);
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

    private Circle restyleLastCircle(Circle circle){
        circle.setFill(Color.BISQUE);
        Text t = (Text) group.getChildren().get(group.getChildren().size() - 1);
        t.setText(". . .");
        return circle;
    }

    private ChoiceBox<String> createChoiceBox(Circle circle){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setVisible(false);
        choiceBox.setLayoutX(circle.getCenterX());
        choiceBox.setLayoutY(circle.getCenterY());
        group.getChildren().add(choiceBox);
        choiceBox.toFront();
        return choiceBox;
    }

    private Circle draw(int numCircles) {
        Circle c = new Circle();
        for (int i = 0; i < numCircles; i++) {
            c = drawTotal((2 * Math.PI)/(MAX_DISPLAY)*i, LENGTH, children.get(i));
        }
        return c;
    }

    private Circle drawTotal(double angle, double length, EntityVisualizer entityVisualizer) {
        Line line = drawLine(angle, length);
        return drawCircle(angle, entityVisualizer, line.getEndX(), line.getEndY());
    }

    private Line drawLine(double angle, double length) {
        Vector radialOffset = vecFromHypotenuse(new Vector(rootCircle.getCenterX(), rootCircle.getCenterY()), rootCircle.getRadius(), angle);
        Vector lineEnd = vecFromHypotenuse(radialOffset, length, angle);
        Line line = new Line(radialOffset.at(0), radialOffset.at(1), lineEnd.at(0), lineEnd.at(1));
        connections.add(line);
        group.getChildren().add(line);
        return line;
    }

    private Circle drawCircle(double angle, EntityVisualizer entityVisualizer, double lineEndX, double lineEndY) {
        double centerX = lineEndX + RADIUS * Math.cos(angle);
        double centerY = lineEndY + RADIUS * Math.sin(angle);
        Circle newCircle = new Circle(centerX, centerY, RADIUS);
        Stop[] stops = new Stop[] { new Stop(0.5f, Color.WHITE), new Stop(0.7f, Color.RED), new Stop(0.9f, Color.MAROON)};
        RadialGradient rg = new RadialGradient(270, 0.8, 0.5, 0.5, 0.7, true, CycleMethod.NO_CYCLE, stops);
        newCircle.setFill(rg);
        //newCircle.setFill(Color.ORCHID);
        group.getChildren().add(newCircle);
        createText(entityVisualizer.root.UIDforObject(), newCircle);
        newCircle.setOnMouseClicked(e -> gameVisualizer.focus(entityVisualizer));
        return newCircle;
    }

    private Vector vecFromHypotenuse(Vector oldPosition, double length, double angle) {
        return oldPosition.add(new Vector(length * Math.cos(angle), length * Math.sin(angle)));
    }

    private int indexByUID(String UID, EntityVisualizer e){
        for (int i = MAX_DISPLAY - 1; i < e.children.size(); i++){
            if (e.children.get(i).root.UIDforObject().equals(UID)){
                return i;
            }
        }
        return -1;
    }

    private void createText(String s, Circle c) {
        Text text = new Text(c.getCenterX(), c.getCenterY(), s.substring(0, 5));
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle("-fx-font-family: \"Georgia\";" + "-fx-font-size: 8px;");
        group.getChildren().add(text);
    }

    protected Group getGroup() {return group;}
}