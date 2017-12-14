package engine.events;

import engine.entities.Entity;
import javafx.scene.Group;

public class AddLayerEvent extends Event{
    Entity myLayer;

    public AddLayerEvent(Entity layer) {
        super(EventType.ADDLAYER.getType());
        myLayer = layer;
    }


    public Group getLayerGroup() {
        return myLayer.getNodes();
    }
}
