package scriptdata

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event
import engine.events.EventType

entity = (Entity) entity

entity.on(EventType.COLLISION.getType(), { Event event ->
    CollisionEvent cEvent = (CollisionEvent) event
    HitBox otherHitBox = cEvent.getCollidedHitBox()
    Entity collidedWith = cEvent.getCollidedWith()
    if(otherHitBox.getTag().equals("triangle")) {

        collidedWith.setProperty("vx", new Double(-collidedWith.getProperty("vx").doubleValue()))
    }

    if(otherHitBox.getTag().equals("rectangle")) {

        collidedWith.setProperty("vx", new Double(-collidedWith.getProperty("vx").doubleValue()))
    }
})
