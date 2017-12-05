package engine.events;

import engine.entities.Entity;

public class CollisionEvent extends Event {
    private Entity collidedWith;
    private String collidedTag;

    public CollisionEvent(Entity collidedWith, String collidedTag) {
        super("collision");
        this.collidedWith = collidedWith;
        this.collidedTag = collidedTag;
    }

    public String getCollidedTag() {
        return collidedTag;
    }

    public Entity getCollidedWith() {
        return collidedWith;
    }
}
