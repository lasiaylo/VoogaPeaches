package engine.visualization;

import engine.entities.Entity;
import javafx.scene.Group;

public class GameVisualizer {

    private Entity root;
    private Group group;
    private TreeVisualizer treeVisualizer;

    public GameVisualizer(Entity root) {
        this.root = root;
        this.group = new Group();
        Visualizer visualizer = new Visualizer(root, null);
        treeVisualizer = new TreeVisualizer(this, visualizer);
        focus(treeVisualizer);
    }

    protected void focus(TreeVisualizer treeVisualizer) {
        group.getChildren().clear();
        group.getChildren().add(treeVisualizer.getGroup());
        treeVisualizer.getGroup().relocate(250, 250);
    }

    public Group getGroup() {
        return group;
    }
}