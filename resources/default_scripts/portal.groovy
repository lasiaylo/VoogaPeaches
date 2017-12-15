import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event ->
    CollisionEvent collide = (CollisionEvent) event

    String portal = hitbox.getTag()

    if ( portal == "portal"){
    println "portal!"}

}