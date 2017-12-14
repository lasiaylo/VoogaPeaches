package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import java.util.ArrayList;
import java.util.List;

public class Visualizer {

    public static final double RADIUS = 35;

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
        group.relocate(0, 0);
        childrenList = new ArrayList<>();
        lines = new ArrayList<>();
        numChildren = entity.getChildren().size();
        initialize();
    }

    private void initialize(){
        addCircle();
        addText(UID.substring(0, 3));
        entity.getChildren().forEach(e -> {childrenList.add(new Visualizer(e, this));});
    }

    private void addCircle(){
        Circle circle = new Circle(0, 0, RADIUS);
        group.getChildren().add(circle);
    }

    private void addText(String string) {
        Text text = new Text(0, 0, string);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle("-fx-font-family: \"Georgia\";" + "-fx-font-size: 18px;");
        group.getChildren().add(text);
    }

    public Visualizer getParentVisualizer(){return parentVisualizer;}

    public List<Visualizer> getChildrenList(){return childrenList;}

    public Group getGroup(){return group;}

    public List<Line> getLines(){return lines;}

    public int getNumChildren(){return numChildren;}

    public String getUID(){return UID;}
}