package scripts

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event
import engine.events.EventType

{ Entity entity, Map<String, Object> bindings, Event event = null ->

    entity = (Entity) entity

    entity.on(EventType.COLLISION.getType(), { Event e ->
        CollisionEvent cEvent = (CollisionEvent) e
        HitBox otherHitBox = cEvent.getCollidedHitBox()
        Entity collidedWith = cEvent.getCollidedWith()
        if (otherHitBox.getTag().equals("triangle")) {
            collidedWith.setProperty("vx", new Double(-collidedWith.getProperty("vx").doubleValue()))
        }
        if (otherHitBox.getTag().equals("rectangle")) {

            collidedWith.setProperty("vx", new Double(-collidedWith.getProperty("vx").doubleValue()))
        }
    })
}