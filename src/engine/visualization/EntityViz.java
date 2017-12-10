package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class EntityViz {
    public static final double RADIUS = 40;
    public static final double CONNECTION_LENGTH = 75;
    public static final int MAX_DISPLAY = 4;

    private Group group;
    private GameVisualizer gameVisualizer;
    private Entity root;
    private Entity parent;
    private EntityViz vizParent;
    private List<EntityViz> children;
    private List<Line> connections = new ArrayList<>();
    private Circle rootCircle;
    private StackPane sp;


    public EntityViz(Entity root, Entity parent, EntityViz vizParent, GameVisualizer gameVizualiser){
        this.root = root;
        this.parent = parent;
        this.vizParent = vizParent;
        this.gameVisualizer = gameVisualizer;
        children = new ArrayList<>();
        group = new Group();
        rootCircle = new Circle(RADIUS);
        rootCircle.setCenterX(0);
        rootCircle.setCenterY(0);
        group.getChildren().add(rootCircle);
        rootCircle.setStroke(Color.BLACK);
        rootCircle.setFill(Color.WHITE);
        Text t = createText(root.UIDforObject(), rootCircle);

        sp = new StackPane();
        sp.getChildren().add(rootCircle);
        sp.getChildren().add(t);

        group.getChildren().add(sp);
        draw(rootCircle, this);
    }

    private Text createText(String s, Circle c) {
        Text text = new Text(c.getCenterX(), c.getCenterY(), s.substring(0, 5));
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle(
                "-fx-font-family: \"Georgia\";" +
                        "-fx-font-size: 8px;"
        );
        group.getChildren().add(text);
        return text;
    }

    public void draw(Circle c, EntityViz e){
        if (e.root.getChildren().isEmpty()){
            return;
        }
        linesFromCircle((Circle) sp.getChildren().get(0), e.root.getChildren().size());
        for (int i = 0; i < e.root.getChildren().size(); i++){
            Line l = connections.get(i);
            Entity child = e.root.getChildren().get(i);
            EntityViz entViz = new EntityViz(child, e.root, e, gameVisualizer);
            Circle newCircle = createCircle(entViz, l.getEndX(), l.getEndY());
            createText(child.UIDforObject(), newCircle);
            draw(newCircle, entViz);
        }
    }

    private Circle createCircle(EntityViz entityViz, double posX, double posY){
        Circle c = new Circle(posX, posY, RADIUS);
        c.setFill(Color.ORCHID);
        group.getChildren().add(c);
        c.setOnMouseClicked(f -> gameVisualizer.focus(entityViz));
        return c;
    }

    private void linesFromCircle(Circle origin, int NUM_LINES){
        for (int i = 0; i < NUM_LINES; i++){
            double angle = (2 * Math.PI)/(NUM_LINES)*i;
            Vector radialOffset = vecFromHypotenuse(new Vector(origin.getCenterX(), origin.getCenterY()), origin.getRadius(), angle);
            Vector lineEnd = vecFromHypotenuse(radialOffset, CONNECTION_LENGTH, angle);
            Line line = new Line(radialOffset.at(0), radialOffset.at(1), lineEnd.at(0), lineEnd.at(1));
            connections.add(line);
            group.getChildren().add(line);
        }
    }

    private Vector vecFromHypotenuse(Vector ogPosition, double length, double angle) {
        return ogPosition.add(new Vector(length * Math.cos(angle), length * Math.sin(angle)));
    }

    protected Group getGroup() {
        return group;
    }
}