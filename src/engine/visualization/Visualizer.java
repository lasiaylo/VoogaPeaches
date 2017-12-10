package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import java.util.ArrayList;
import java.util.Collection;

public class Visualizer {

    public static final double RADIUS = 40;

    private Entity entity;
    private Visualizer parentVisualizer;
    private String UID;
    private Group group;
    private Collection<Visualizer> childrenList;

    public Visualizer(Entity entity, Visualizer parentVisualizer){
        this.entity = entity;
        this.parentVisualizer = parentVisualizer;
        this.UID = entity.UIDforObject();
        this.group = new Group();
        childrenList = new ArrayList<>();
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

    public int getNumChildren(){
        return childrenList.size();
    }

    public String getUID(){
        return UID;
    }

    public Group getGroup() {
        return group;
    }
}