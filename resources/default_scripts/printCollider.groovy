package default_scripts

import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event ->
    engine.events.CollisionEvent collide = (engine.events.CollisionEvent) event
    println "Here's the object that you're colliding with!"
    println (collide.getCollidedHitBox().getTag())
}