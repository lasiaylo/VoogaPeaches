package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;
import java.util.List;

public class Visualizer {

    private static final double RADIUS = 75;
    private static final String FONT = "-fx-font-family: \"Georgia\";" + "-fx-font-size: 18px;";

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
        Circle root = addCircle(0, 0, RADIUS);
        styleRoot(root);
        addText(UID, root);
        addChildren();
    }

    protected Circle addCircle(double centerX, double centerY, double radius){
        Circle circle = new Circle(centerX, centerY, radius);
        group.getChildren().add(circle);
        return circle;
    }

    protected void addText(String string, Circle circle) {
        Text text = new Text(circle.getCenterX() - 18.0, circle.getCenterY(), string.substring(0, 5));
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle(FONT);
        group.getChildren().add(text);
    }

    private void addChildren(){
        entity.getChildren().forEach(e -> {childrenList.add(new Visualizer(e, this));});
    }

    private void styleRoot(Circle circle){
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
    }

    private void styleParent(Circle circle) {
        circle.setFill(Color.CORNFLOWERBLUE);
    }

    protected void styleChild(Circle circle) {
        circle.setFill(Color.ORCHID);
    }

    protected ChoiceBox<String> addChoiceBox(Circle circle){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        circle.setFill(Color.BISQUE);
        choiceBox.setVisible(false);
        choiceBox.setLayoutX(circle.getCenterX());
        choiceBox.setLayoutY(circle.getCenterY());
        group.getChildren().add(choiceBox);
        choiceBox.toFront();
        return choiceBox;
    }

    public Visualizer getParentVisualizer(){return parentVisualizer;}

    public List<Visualizer> getChildrenList(){return childrenList;}

    public Group getGroup(){return group;}

    public List<Line> getLines(){return lines;}

    public int getNumChildren(){return numChildren;}

    public String getUID(){return UID;}
}