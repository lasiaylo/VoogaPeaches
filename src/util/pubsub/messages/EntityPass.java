package util.pubsub.messages;

import engine.entities.Entity;
import javafx.scene.image.Image;

public class EntityPass extends Message {

    private Entity entity;
    private Image image;

    public EntityPass(Entity entity, Image image) {
        this.entity = entity;
        this.image = image;
    }

    public Entity getEntity() {
        return entity;
    }

    public Image getImage() {
        return image;
    }
}
