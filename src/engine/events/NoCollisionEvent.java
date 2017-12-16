package engine.events;

import engine.collisions.HitBox;
import engine.entities.Entity;

public class NoCollisionEvent extends Event {
    private HitBox collidedHitBox;
    private Entity collidedWith;

    public NoCollisionEvent(HitBox hitBox, Entity collidedWith) {
        super(EventType.COLLISION.getType());
        this.collidedHitBox = hitBox;
        this.collidedWith = collidedWith;
    }

    public HitBox getCollidedHitBox() {
        return collidedHitBox;
    }

    public Entity getCollidedWith() {
        return collidedWith;
    }
}
