package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;

public class GameVisualizer {

    private Entity root;
    private EntityVisualizer vizRoot;
    private Group group;
    private TreeVisualizer treeVisualizer;

    public GameVisualizer(Entity root) {
        this.root = root;
        this.group = new Group();
        vizRoot = new EntityVisualizer(this, root, null,null);
        focus(vizRoot);

//        Visualizer visualizer = new Visualizer(root, null);
//        treeVisualizer = new TreeVisualizer(this, visualizer);
//        focus(visualizer);
    }

    protected void focus(EntityVisualizer entityVisualizer) {
        group.getChildren().clear();
        group.getChildren().add(entityVisualizer.getGroup());
        entityVisualizer.getGroup().relocate(200, 200);
    }

    protected void focus(Visualizer visualizer) {
        group.getChildren().clear();
        group.getChildren().add(visualizer.getGroup());
        visualizer.getGroup().relocate(250, 250);
    }

    public Group getGroup() {
        return group;
    }
}