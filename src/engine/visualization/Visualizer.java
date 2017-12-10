package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class Visualizer {

    public static final double RADIUS = 20;
    public static final double LENGTH = 75;

    private Entity entity;
    private Visualizer parentVisualizer;
    private String UID;
    private Group group;
    private List<Visualizer> childrenList;
    private List<Line> lines;
    private int numChildren;

    public Visualizer(Entity entity, Visualizer parentVisualizer){
        this.entity = entity;
        this.parentVisualizer = parentVisualizer;
        this.UID = entity.UIDforObject();
        this.group = new Group();
        childrenList = new ArrayList<>();
        lines = new ArrayList<>();
        numChildren = entity.getChildren().size();
        initialize();
    }

    private void initialize(){
        if (!entity.getChildren().isEmpty()){
            for (Entity e : entity.getChildren()){
                childrenList.add(new Visualizer(e, this));
            }
        }
        Circle circle = addCircle();
        addText(UID, circle);
        linesFromCircle(circle, numChildren);
    }

    private Circle addCircle(){
        Circle circle = new Circle(RADIUS);
        group.getChildren().add(circle);
        return circle;
    }

    private void addText(String s, Circle c) {
        Text text = new Text(c.getCenterX(), c.getCenterY(), s.substring(0, 5));
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle(
                "-fx-font-family: \"Georgia\";" +
                        "-fx-font-size: 8px;"
        );
        group.getChildren().add(text);
    }

    private void linesFromCircle(Circle origin, int numLines){
        for (int i = 0; i < numLines; i++){
            double angle = (2 * Math.PI)/(numLines)*i;
            Vector lineBegin = vecFromHypotenuse(new Vector(origin.getCenterX(), origin.getCenterY()), origin.getRadius(), angle);
            Vector lineEnd = vecFromHypotenuse(lineBegin, LENGTH, angle);
            Line line = new Line(lineBegin.at(0), lineBegin.at(1), lineEnd.at(0), lineEnd.at(1));
            lines.add(line);
            group.getChildren().add(line);
        }
    }

    private Vector vecFromHypotenuse(Vector oldPosition, double length, double angle) {
        return oldPosition.add(new Vector(length * Math.cos(angle), length * Math.sin(angle)));
    }

    public List<Visualizer> getChildrenList(){
        return childrenList;
    }

    public Group getGroup() {
        return group;
    }

    public List<Line> getLines() {
        return lines;
    }

    public int getNumChildren(){
        return numChildren;
    }

    public String getUID(){
        return UID;
    }
}