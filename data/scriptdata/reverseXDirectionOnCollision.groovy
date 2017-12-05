package scriptdata

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event

entity = (Entity) entity

entity.on("collision", { Event event ->
    CollisionEvent cEvent = (CollisionEvent) event
    HitBox otherHitBox = cEvent.getCollidedHitBox()
    Entity collidedWith = cEvent.getCollidedWith()

    if(otherHitBox.getTag().equals("triangle")) {
//        println "1"
//        println collidedWith.getProperty("vx")

        collidedWith.setProperty("vx", new Double(-entity.getProperty("vx").doubleValue()))

//        println "2"
//        println collidedWith.getProperty("vx")

    }
})
