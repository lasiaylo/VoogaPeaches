package authoring.panels.tabbable;

import authoring.Panel;
import engine.entities.Entity;
import javafx.scene.Group;
import javafx.scene.layout.Region;

import java.util.List;

public class HitBoxPanel implements Panel {
    private static final String TITLE = "Create or Add Hitboxes!";
    private Entity entity;
    private Group group;
    private List<Double> points;

    public HitBoxPanel(Entity entity) {
        this.entity = entity;
        this.group = new Group();
        group.getChildren().add(entity.getNodes());

    }

    @Override
    public Region getRegion() {
        return null;
    }

    @Override
    public String title() {
        return TITLE;
    }
}
