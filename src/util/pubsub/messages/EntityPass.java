package util.pubsub.messages;

import engine.entities.Entity;
import javafx.scene.image.Image;

/**
 * Message that passes entities to panels
 * @author Albert
 */
public class EntityPass extends Message {

    private Entity entity;
    private Image image;

    /**
     * Creates a new EntityPass
     * @param entity    entity to pass to panels
     * @param image     imageview of associated entity
     */
    public EntityPass(Entity entity, Image image) {
        this.entity = entity;
        this.image = image;
    }

    /**
     * @return  entity passed in message
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * @return  image passed in message
     */
    public Image getImage() {
        return image;
    }
}
