package engine.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static engine.visualization.EntityViz.CONNECTION_LENGTH;

public class TreeVisualizer {

    private GameVisualizer gameVisualizer;
    private Visualizer visualizer;
    private Group group;

    public TreeVisualizer(GameVisualizer gameVisualizer, Visualizer visualizer){
        this.gameVisualizer = gameVisualizer;
        this.visualizer = visualizer;
        group = new Group();
        styleRoot(visualizer);
        recurse(visualizer);
    }

    private void styleRoot(Visualizer visualizer){
        //visualizer.getGroup().relocate(0, 0);
        Circle circle = (Circle) visualizer.getGroup().getChildren().get(0);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
    }

    private void recurse(Visualizer visualizer){
        group.getChildren().add(visualizer.getGroup());
        if (visualizer.getNumChildren() <= 0){
            return;
        }
        else{
            for (int i = 0; i < visualizer.getChildrenList().size(); i++) {
                Visualizer child = visualizer.getChildrenList().get(i);
                Line l = visualizer.getLines().get(i);
                child.getGroup().relocate(l.getEndX(), l.getEndY());
                ((Circle) child.getGroup().getChildren().get(0)).setFill(Color.ORCHID);
                recurse(child);
            }
        }
    }

    public Group getGroup() {
        return group;
    }
}