package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityVisualizer {
    public static final double RADIUS = 20;
    public static final double CONNECTION_Y_LENGTH = 30;
    public static final int MAX_DISPLAY = 4;

    private Group group;
    private Circle circle;
    private List<EntityVisualizer> children;
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
        if(children.size() > MAX_DISPLAY) {
            redraw();
        } else if(children.size() == MAX_DISPLAY){

        }
    }

    public Group getGroup() {
        return group;
    }

    private void redraw() {
        children.forEach(e -> group.getChildren().removeAll(e.getGroup())); // check to make sure not null pointer exception
        group.getChildren().removeAll(connections);
        double angle = 360. / ( children.size() + 2 );
//        drawLine()
    }

    private void drawLine(double angle) {
//        Line line = new Line()
    }
}
