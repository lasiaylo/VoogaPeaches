package engine.events;

import engine.entities.Entity;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class AddLayerEvent extends Event{
    Entity myLayer;

    public AddLayerEvent(Entity layer) {
        super("addLayer");
    }


    public Entity getLayer() {
        return myLayer;
    }
}
