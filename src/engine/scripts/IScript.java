package engine.scripts;

import engine.entities.Entity;
import javafx.scene.Node;

public interface IScript {
    void execute(Entity entity);

    Node getNode();
}
