package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;

public class GameVisualizer {
    private Entity root;
    private Group group;
    private EntityVisualizer vizRoot;
    private EntityViz vizRoot;
    private TreeVisualizer;

    public GameVisualizer(Entity root) {
        this.root = root;
        this.group = new Group();
        vizRoot = new EntityVisualizer(this, root, null,null, 0, 0);
        vizRoot.drawRoot();
        vizRoot = new EntityViz(root, null, null, this);
        Visualizer v = new Visualizer(root, null);
        vizRoot = new TreeVisualizer(this, v);

        focus(vizRoot);
    }

    protected void focus(EntityViz entityVisualizer) {
        group.getChildren().clear();
        group.getChildren().add(entityVisualizer.getGroup());
        entityVisualizer.getGroup().relocate(200, 200);
    }

    public Group getGroup() {
        return group;
    }

}