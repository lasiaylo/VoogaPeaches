package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;

public class GameVisualizer {
    private Entity root;
    private Group group;
    private EntityVisualizer vizRoot;

    public GameVisualizer(Entity root) {
        this.root = root;
        this.group = new Group();
        vizRoot = new EntityVisualizer(this, root, null,null, 0, 0);
        vizRoot.drawRoot();
        focus(vizRoot);
    }

    protected void focus(EntityVisualizer entityVisualizer) {
        group.getChildren().clear();
        group.getChildren().add(entityVisualizer.getGroup());
        entityVisualizer.getGroup().relocate(200, 200);
    }

    public Group getGroup() {
        return group;
    }

}