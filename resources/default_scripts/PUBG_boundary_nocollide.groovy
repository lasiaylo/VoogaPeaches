package scripts

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event
import engine.events.MoveEvent
import engine.events.NoCollisionEvent

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (NoCollisionEvent) event
    boundary = (HitBox) entity.getHitBoxes().get(0)
    other = (HitBox) event.getCollidedHitBox()

    if (boundary.getTag().equals("boundary")) {
        if (other.getTag().equals("enemyBody")) {
            event.getCollidedWith().setProperty("vx", -((Number)event.getCollidedWith().getProperty("vx")).doubleValue())
            event.getCollidedWith().setProperty("vy", -((Number)event.getCollidedWith().getProperty("vy")).doubleValue())
        }
        if (other.getTag().equals("player")) {
            event.getCollidedWith().getParent().remove(event.getCollidedWith())
            //todo game over
        }
    }
}

