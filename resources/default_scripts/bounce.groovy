import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event ->
    CollisionEvent collide = (CollisionEvent) event

    HitBox hitbox = collide.getCollidedHitBox()
    Entity myEntity = entity
    Entity otherEntity = collide.getCollidedWith()
    def bounce = bindings.get("bounce")

    def myY = myEntity.getProperty("y") - myEntity.getProperty("height")
    println "gonna bounce!"
    if (otherEntity.getProperty("y") >= myY && otherEntity.getProperty("isFalling")) {
        otherEntity.setProperty("vy", bounce)
        println "bounce!"
    }
}