package engine.events;

import engine.entities.Entity;
import javafx.scene.Group;

/**
 * An Event that specifies adding a layer
 * @author estellehe
 */
public class AddLayerEvent extends Event{
    private Entity myLayer;

    /**
     * Creates a new AddLayerEvent
     * @param layer entity layer to add
     */
    public AddLayerEvent(Entity layer) {
        super(EventType.ADDLAYER.getType());
        myLayer = layer;
    }

    /**
     * @return nodes of entity
     */
    public Group getLayerGroup() {
        return myLayer.getNodes();
    }
}
