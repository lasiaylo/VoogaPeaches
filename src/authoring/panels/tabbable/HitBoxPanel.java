package authoring.panels.tabbable;

import authoring.Panel;
import authoring.buttons.CustomButton;
import authoring.buttons.strategies.HitBoxSave;
import engine.collisions.HitBox;
import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import util.ErrorDisplay;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class HitBoxPanel implements Panel {
    private static final double RADIUS = 5;
    private static final String TITLE = "Create or Add Hitboxes!";
    public static final int MIN_HEIGHT = 500;

    private Entity entity;
    private Pane entityView = new Pane();
    private VBox region = new VBox();
    private TextField textField = new TextField();
    private List<Double> points = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();

    public HitBoxPanel() {
        entityView.setMinHeight(MIN_HEIGHT);
        region.getChildren().add(entityView);
        entityView.setOnMouseClicked(e -> addPoint(e));
        region.getChildren().add(textField);
        region.getChildren().add(new CustomButton(new HitBoxSave(this), "Save").getButton());
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
        entityView.getChildren().clear();
        entityView.getChildren().add(entity.getNodes());
//        entityView

    }

//    private void dragHandle(MouseDragEvent dragEvent) {
//        for(int i = 0; i < points.size(); i += 2) {
//            double x = points.get(i);
//            double y = points.get(i + 1);
//            if(checkRadius(x, y, dragEvent)) {
//
//            }
//        }
//    }

    private boolean checkRadius(double x, double y, MouseDragEvent dragEvent) {
        Vector v = new Vector(dragEvent.getX() - x, dragEvent.getY() - y);
        return v.norm() < RADIUS;
    }

    private void addPoint(MouseEvent event) {
        if(points.size() > 2) {
            Line line = new Line(0, 0, 0, 0);
        }
        points.add(event.getX());
        points.add(event.getY());
    }

    public void createHitBox() {
        try {
            System.out.println("hell yeah");
            HitBox hitBox = new HitBox(points, (Double) entity.getProperty("x"), (Double) entity.getProperty("y"), textField.getText());
            entity.addHitBox(hitBox);
            entity.getNodes().relocate((double) entity.getProperty("x"), (double) entity.getProperty("y"));
        } catch (NullPointerException e) {
            // do nothing
        }
        // move hit boxes?
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public String title() {
        return TITLE;
    }
}
