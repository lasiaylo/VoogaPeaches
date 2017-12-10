package engine.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import util.math.num.Vector;

import java.util.*;

public class TreeVisualizer {

    public static final double LENGTH = 100;

    private GameVisualizer gameVisualizer;
    private Visualizer visualizer;
    private Group group;

    public TreeVisualizer(GameVisualizer gameVisualizer, Visualizer visualizer){
        this.gameVisualizer = gameVisualizer;
        this.visualizer = visualizer;
        group = new Group();
        styleRoot(visualizer);
        BFS(visualizer);

    }

    private void styleRoot(Visualizer visualizer){
        Circle circle = (Circle) visualizer.getGroup().getChildren().get(0);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
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
            for (int i = 0; i < visualizer.getLines().size(); i++) {
                Visualizer child = visualizer.getChildrenList().get(i);
                ((Circle) child.getGroup().getChildren().get(0)).setFill(Color.ORCHID);
            }
        }
    }

    private void BFS(Visualizer visualizer)
    {
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
        for (int i = 0; i < visualizer.getNumChildren(); i++){
            double angle = (2 * Math.PI)/(visualizer.getNumChildren())*i;
            Vector lineBegin = vecFromHypotenuse(new Vector(origin.getCenterX(), origin.getCenterY()), origin.getRadius(), angle);
            Vector lineEnd = vecFromHypotenuse(lineBegin, LENGTH, angle);
            Line line = new Line(lineBegin.at(0), lineBegin.at(1), lineEnd.at(0), lineEnd.at(1));
            visualizer.getLines().add(line);
            visualizer.getGroup().getChildren().add(line);
        }
    }

    public Group getGroup() {return group;}
}