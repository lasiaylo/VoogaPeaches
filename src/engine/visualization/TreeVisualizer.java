package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import util.math.num.Vector;
import java.util.*;

public class TreeVisualizer {

    private static final double RADIUS = 50;
    public static final double LENGTH = 50;
    public static final int MAX_DISPLAY = 4;

    private GameVisualizer gameVisualizer;
    private Visualizer visualizer;
    private Group group;

    public TreeVisualizer(GameVisualizer gameVisualizer, Visualizer visualizer){
        this.gameVisualizer = gameVisualizer;
        this.visualizer = visualizer;
        group = new Group();
        //BFS(visualizer);
        draw(visualizer);
    }

    private void draw(Visualizer visualizer) {
        int children = Math.min(MAX_DISPLAY, visualizer.getNumChildren());
        for (int i = 0; i < visualizer.getNumChildren(); i++) {
            double angle = (2 * Math.PI)/(MAX_DISPLAY)*i;
            Line line = drawLine(angle);
            Visualizer child = visualizer.getChildrenList().get(i);
            drawCircle(child, line, angle);
        }
        if (visualizer.getNumChildren() > MAX_DISPLAY){

        }
    }

    private void drawCircle(Visualizer child, Line line, double angle){
        double centerX = line.getEndX() + RADIUS * Math.cos(angle);
        double centerY = line.getEndY() + RADIUS * Math.sin(angle);
        Circle circle = child.addCircle(centerX, centerY, RADIUS);
        child.styleChild(circle);
        circle.setOnMouseClicked(e -> focus(child));
        child.addText(child.getUID(), circle);
        group.getChildren().add(child.getGroup());
    }

    private void focus(Visualizer child){
        group.getChildren().clear();
        group.getChildren().add(child.getGroup());
        draw(child);
    }

//    private void drawChildren(Visualizer visualizer) {
//        if (visualizer.getNumChildren() <= MAX_DISPLAY) {draw(visualizer);}
//        else {
//            Circle lastCircle = restyleLastCircle(draw(MAX_DISPLAY));
//            ChoiceBox<String> cb = createChoiceBox(lastCircle);
//            lastCircle.setOnMouseClicked(f -> {
//                for (int i = MAX_DISPLAY - 1; i < children.size(); i++){
//                    String UID = children.get(i).root.UIDforObject();
//                    if (!cb.getItems().contains(UID)){
//                        cb.getItems().add(UID);
//                    }
//                }
//                cb.show();
//                cb.setOnAction(g -> {
//                    if (cb.getValue() != null) {
//                        gameVisualizer.focus(children.get(indexByUID(cb.getValue(), this)));
//                        cb.valueProperty().set(null);
//                    }
//                });
//            });
//        }
//    }

//    private void BFS(Visualizer visualizer){
//        Map<String, Boolean> visited = new HashMap<>();
//        Queue<Visualizer> queue = new LinkedList<>();
//        visited.put(visualizer.getUID(), true);
//        queue.add(visualizer);
//        while (queue.size() != 0)
//        {
//            Visualizer currentVisualizer = queue.poll();
//            draw(currentVisualizer);
//            Iterator<Visualizer> i = currentVisualizer.getChildrenList().listIterator();
//            while (i.hasNext())
//            {
//                Visualizer nextVisualizer = i.next();
//                if (!visited.containsKey(nextVisualizer.getUID()))
//                {
//                    visited.put(nextVisualizer.getUID(), true);
//                    queue.add(nextVisualizer);
//                }
//            }
//        }
//    }

    private Line drawLine(double angle) {
        Circle origin = (Circle) visualizer.getGroup().getChildren().get(0);
        Vector lineBegin = calculateVector(new Vector(origin.getCenterX(), origin.getCenterY()), origin.getRadius(), angle);
        Vector lineEnd = calculateVector(lineBegin, LENGTH, angle);
        Line line = new Line(lineBegin.at(0), lineBegin.at(1), lineEnd.at(0), lineEnd.at(1));
        visualizer.getLines().add(line);
        group.getChildren().add(line);
        return line;
    }

    private Vector calculateVector(Vector oldPosition, double length, double angle) {
        return oldPosition.add(new Vector(length * Math.cos(angle), length * Math.sin(angle)));
    }

    private int indexByUID(String UID, Visualizer visualizer){
        for (int i = MAX_DISPLAY - 1; i < visualizer.getNumChildren(); i++){
            if (visualizer.getChildrenList().get(i).getUID().equals(UID)){
                return i;
            }
        }
        return -1;
    }

    public Group getGroup() {return group;}
}