package util.pubsub.messages;

import engine.entities.Entity;

public class EntityPass extends Message {
    private Entity entity;
    public EntityPass(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
