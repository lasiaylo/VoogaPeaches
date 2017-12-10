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
        if (numChildren > 0){
            for (Entity e : entity.getChildren()){
                childrenList.add(new Visualizer(e, this));
            }
        }
        Circle circle = addCircle();
        addText(UID, circle);
    }

    private Circle addCircle(){
        Circle circle = new Circle(RADIUS);
        group.getChildren().add(circle);
        return circle;
    }

    private void addText(String s, Circle circle) {
        Text text = new Text(circle.getCenterX(), circle.getCenterY(), s.substring(0, 5));
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle(
                "-fx-font-family: \"Georgia\";" +
                        "-fx-font-size: 8px;"
        );
        group.getChildren().add(text);
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