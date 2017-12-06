package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class EntityVisualizer {
    public static final double RADIUS = 20;
    public static final double CONNECTION_LENGTH = 30;
    public static final int MAX_DISPLAY = 4;

    private Group group;
    private Circle circle;
    private List<EntityVisualizer> children;
    private List<Circle> childrenVis;
    private List<Line> connections = new ArrayList<>();
    private Entity entity;

    public EntityVisualizer(Entity entity, Vector position) {
        this.entity = entity;

        circle = new Circle(RADIUS);
        circle.setCenterX(position.at(0));
        circle.setCenterY(position.at(1));

        group = new Group();
        group.getChildren().add(circle);
    }

    public void addChild(EntityVisualizer eVisualizer) {
        children.add(eVisualizer);
        group.getChildren().addAll(eVisualizer.getGroup());
        if(children.size() <= MAX_DISPLAY) {
            redraw();
        } else if(children.size() == MAX_DISPLAY + 1){

        }
    }

    public Group getGroup() {
        return group;
    }

    private void redraw() {
        children.forEach(e -> group.getChildren().removeAll(childrenVis)); // check to make sure not null pointer exception
        group.getChildren().removeAll(connections);
        double angle = 360. / ( children.size() + 1 );
        for(int i = 1; i < children.size() + 1; i++) {
            drawLine(angle * i);
            drawCircle(angle * i);
        }
    }

    private void drawLine(double angle) {
        Vector radialOffset = vecFromHypotenuse(new Vector(circle.getCenterX(), circle.getCenterY()), circle.getRadius(), angle);
        Vector lineEnd = vecFromHypotenuse(radialOffset, CONNECTION_LENGTH, angle);

        Line line = new Line(radialOffset.at(0), radialOffset.at(1), lineEnd.at(0), lineEnd.at(1));
        connections.add(line);
        group.getChildren().add(line);
    }

    private void drawCircle(double angle) {
        Circle newCircle = new Circle(RADIUS);
        newCircle.setCenterX(circle.getCenterX() + 2 * circle.getRadius());
        newCircle.setCenterY(circle.getCenterY() + 2 * circle.getRadius());
        group.getChildren().add(newCircle);
        childrenVis.add(newCircle);
    }

    private Vector vecFromHypotenuse(Vector ogPosition, double length, double angle) {
        return ogPosition.add(new Vector(length * Math.cos(angle), length * Math.sin(angle)));
    }
}
