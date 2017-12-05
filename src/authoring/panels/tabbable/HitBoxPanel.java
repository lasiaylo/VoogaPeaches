package authoring.panels.tabbable;

import authoring.Panel;
import engine.collisions.HitBox;
import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class HitBoxPanel implements Panel {
    private static final double RADIUS = 5;

    private static final String TITLE = "Create or Add Hitboxes!";
    private Entity entity;
    private Group entityView = new Group();
    private VBox region = new VBox();
    private TextField textField = new TextField();
    private List<Double> points = new ArrayList<>();

    public HitBoxPanel(Entity entity) {
        this.entity = entity;
        region.getChildren().add(entityView);
        entityView.getChildren().add(entity.getNodes());
        entityView.setOnMouseClicked(e -> addPoint(e));
        region.getChildren().add(textField);
    }

    private void dragHandle(MouseDragEvent dragEvent) {
        for(int i = 0; i < points.size(); i += 2) {
            double x = points.get(i);
            double y = points.get(i + 1);
//            if()
        }
    }

    private boolean checkRadius(double x, double y, MouseDragEvent dragEvent) {
        Vector v = new Vector(dragEvent.getX() - x, dragEvent.getY() - y);
        return v.norm() < RADIUS;
    }

    private void addPoint(MouseEvent event) {
        points.add(event.getX());
        points.add(event.getY());
    }

    public void createHitBox() {
        HitBox hitBox = new HitBox(points, (Double) entity.getProperty("x"),
                (Double) entity.getProperty("y"), textField.getText());

        entity.addHitBox(hitBox);
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
