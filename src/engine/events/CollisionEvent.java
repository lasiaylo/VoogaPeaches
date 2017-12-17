package engine.events;

import engine.collisions.HitBox;
import engine.entities.Entity;

/**
 * An event that specifies a collision
 * @author Albert
 */
public class CollisionEvent extends Event {
    private HitBox collidedHitBox;
    private Entity collidedWith;

    /**
     * Creates a new CollisionEvent
     * @param hitBox        hitbox collided with
     * @param collidedWith  entity collided with
     */
    public CollisionEvent(HitBox hitBox, Entity collidedWith) {
        super(EventType.COLLISION.getType());
        this.collidedHitBox = hitBox;
        this.collidedWith = collidedWith;
    }

    /**
     *
     * @return  hitbox collided with
     */
    public HitBox getCollidedHitBox() {
        return collidedHitBox;
    }

    /**
     *
     * @return  entity collided with
     */
    public Entity getCollidedWith() {
        return collidedWith;
    }
}
