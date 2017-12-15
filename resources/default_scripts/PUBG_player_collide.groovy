package scripts

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event
import engine.events.MoveEvent

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (CollisionEvent) event
    player = (HitBox) entity.getHitBoxes().get(0)
    other = (HitBox) event.getCollidedHitBox()

    if (player.getTag().equals("player")) {
        if (other.getTag().equals("enemyView")) {
            entity.getParent().remove(entity)
            //todo game over
        }
        if (other.getTag().equals("enemyBody")) {
            event.getCollidedWith().getParent().remove(event.getCollidedWith())
            //todo increase the boundary radius
        }
    }
}
