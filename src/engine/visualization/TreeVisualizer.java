package engine.visualization;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static engine.visualization.EntityViz.CONNECTION_LENGTH;

public class TreeVisualizer {

    private GameVisualizer gameVisualizer;
    private Visualizer visualizer;
    private Group group;
    private Collection<Line> connections;

    public TreeVisualizer(GameVisualizer gameVisualizer, Visualizer visualizer){
        this.gameVisualizer = gameVisualizer;
        this.visualizer = visualizer;
        group = new Group();
        connections = new ArrayList<>();
        linesFromCircle(visualizer.getGroup(), visualizer.getNumChildren());
    }

    private void recurse(Visualizer visualizer){
        //visualizer.getGroup().relocate();
        if (visualizer.getNumChildren() <= 0){
            return;
        }
        group.getChildren().add(visualizer.getGroup());

    }

    private void linesFromCircle(Group group, int numLines){
        Circle origin = (Circle) group.getChildren().get(0);
        for (int i = 0; i < numLines; i++){
            double angle = (2 * Math.PI)/(numLines)*i;
            Vector radialOffset = vecFromHypotenuse(new Vector(origin.getCenterX(), origin.getCenterY()), origin.getRadius(), angle);
            Vector lineEnd = vecFromHypotenuse(radialOffset, CONNECTION_LENGTH, angle);
            Line line = new Line(radialOffset.at(0), radialOffset.at(1), lineEnd.at(0), lineEnd.at(1));
            connections.add(line);
            group.getChildren().add(line);
        }
    }

    private Vector vecFromHypotenuse(Vector oldPosition, double length, double angle) {
        return oldPosition.add(new Vector(length * Math.cos(angle), length * Math.sin(angle)));
    }

    public Group getGroup() {
        return group;
    }
}