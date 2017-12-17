package engine.events;

import engine.collisions.HitBox;
import engine.entities.Entity;

/**
 * An event that specifies no collision
 * @author estellehe
 */
public class NoCollisionEvent extends Event {
    private HitBox notCollidedHitBox;
    private Entity notCollidedWith;

    /**
     * Creates a new NoCollisionEvent
     * @param hitBox        hitbox not collided with
     * @param notCollidedWith  entity not collided with
     */
    public NoCollisionEvent(HitBox hitBox, Entity notCollidedWith) {
        super(EventType.COLLISION.getType());
        this.notCollidedHitBox = hitBox;
        this.notCollidedWith = notCollidedWith;
    }

    /**
     * @return  not collided with hit box
     */
    public HitBox getCollidedHitBox() {
        return notCollidedHitBox;
    }

    /**
     * @return  entity not collided with
     */
    public Entity getCollidedWith() {
        return notCollidedWith;
    }
}
