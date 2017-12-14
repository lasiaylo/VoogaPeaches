package engine.visualization;

import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import util.math.num.Vector;

import java.util.*;

public class TreeVisualizer {

    public static final double LENGTH = 100;
    public static final int MAX_DISPLAY = 4;

    private GameVisualizer gameVisualizer;
    private Visualizer visualizer;
    private Group group;

    public TreeVisualizer(GameVisualizer gameVisualizer, Visualizer visualizer){
        this.gameVisualizer = gameVisualizer;
        this.visualizer = visualizer;
        group = new Group();
        styleRoot(visualizer);
        //BFS(visualizer);
        draw(visualizer);
        drawChildren(visualizer);
    }

    private void styleRoot(Visualizer visualizer){
        Circle circle = (Circle) visualizer.getGroup().getChildren().get(0);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
    }

    private void styleChild(Circle circle, ChoiceBox choiceBox){
        circle.setFill(Color.BISQUE);
        choiceBox.setVisible(false);
        choiceBox.setLayoutX(circle.getCenterX());
        choiceBox.setLayoutY(circle.getCenterY());
        group.getChildren().add(choiceBox);
        choiceBox.toFront();
    }

    private void draw(Visualizer visualizer){
        group.getChildren().add(visualizer.getGroup());
        visualizer.getGroup().relocate(0, 0);
        if (visualizer.getParentVisualizer() != null){
            Line l = visualizer.getParentVisualizer().getLines().get(0);
            visualizer.getParentVisualizer().getLines().remove(0);
            visualizer.getGroup().relocate(l.getEndX(), l.getEndY());
        }
        if (visualizer.getNumChildren() > 0){
            linesFromCircle(visualizer);
            for (int i = 0; i < visualizer.getChildrenList().size(); i++) {
                Visualizer child = visualizer.getChildrenList().get(i);
                Circle circle = ((Circle) child.getGroup().getChildren().get(0));
                circle.setFill(Color.ORCHID);
                circle.setOnMouseClicked(e -> gameVisualizer.focus(child));
            }
        }
    }

    private void drawChildren(Visualizer visualizer) {
        int children = Math.min(MAX_DISPLAY, visualizer.getNumChildren());
        for (int i = 0; i < children; i++) {
            draw(visualizer.getChildrenList().get(i));
        }
        if (visualizer.getNumChildren() > MAX_DISPLAY) {
            Visualizer child = visualizer.getChildrenList().get(MAX_DISPLAY);
            draw(child);
            Circle circle = (Circle) child.getGroup().getChildren().get(0);
            ChoiceBox<String> choiceBox = new ChoiceBox<>();
            styleChild(circle, choiceBox);
            circle.setOnMouseClicked(f -> {
                for (int j = MAX_DISPLAY - 1; j < visualizer.getNumChildren(); j++){
                    if (!choiceBox.getItems().contains(visualizer.getUID())){
                        choiceBox.getItems().add(visualizer.getUID());
                    }
                }
                choiceBox.show();
                choiceBox.setOnAction(g -> {
                    if (choiceBox.getValue() != null) {
                        gameVisualizer.focus(visualizer.getChildrenList().get(indexByUID(choiceBox.getValue(), visualizer)));
                        choiceBox.valueProperty().set(null);
                    }
                });
            });
        }
    }

    private void BFS(Visualizer visualizer){
        Map<String, Boolean> visited = new HashMap<>();
        Queue<Visualizer> queue = new LinkedList<>();
        visited.put(visualizer.getUID(), true);
        queue.add(visualizer);
        while (queue.size() != 0)
        {
            Visualizer currentVisualizer = queue.poll();
            draw(currentVisualizer);
            Iterator<Visualizer> i = currentVisualizer.getChildrenList().listIterator();
            while (i.hasNext())
            {
                Visualizer nextVisualizer = i.next();
                if (!visited.containsKey(nextVisualizer.getUID()))
                {
                    visited.put(nextVisualizer.getUID(), true);
                    queue.add(nextVisualizer);
                }
            }
        }
    }

    private Vector vecFromHypotenuse(Vector oldPosition, double length, double angle) {
        return oldPosition.add(new Vector(length * Math.cos(angle), length * Math.sin(angle)));
    }

    private void linesFromCircle(Visualizer visualizer){
        Circle origin = (Circle) visualizer.getGroup().getChildren().get(0);
        for (int i = 0; i < MAX_DISPLAY; i++){
            double angle = (2 * Math.PI)/(MAX_DISPLAY)*i;
            Vector lineBegin = vecFromHypotenuse(new Vector(origin.getCenterX(), origin.getCenterY()), origin.getRadius(), angle);
            Vector lineEnd = vecFromHypotenuse(lineBegin, LENGTH, angle);
            Line line = new Line(lineBegin.at(0), lineBegin.at(1), lineEnd.at(0), lineEnd.at(1));
            visualizer.getLines().add(line);
            visualizer.getGroup().getChildren().add(line);
        }
    }

    private int indexByUID(String UID, Visualizer visualizer){
        for (int i = MAX_DISPLAY; i < visualizer.getNumChildren(); i++){
            if (visualizer.getChildrenList().get(i).getUID().equals(UID)){
                return i;
            }
        }
        return -1;
    }

    public Group getGroup() {return group;}
}