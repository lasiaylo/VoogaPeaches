import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event ->
    CollisionEvent collide = (CollisionEvent) event

    HitBox hitbox = collide.getCollidedHitBox()
    Entity myEntity = entity
    Entity otherEntity = collide.getCollidedWith()
    String other = hitbox.getTag()

    def myY = myEntity.getProperty("y") - myEntity.getProperty("height")
    if (otherEntity.getProperty("y") >= myY && otherEntity.getProperty("isFalling")){
        otherEntity.setProperty("y",myY-1)
        otherEntity.setProperty("isFalling",false)
        println "landed!"
    }


//    def myX = myEntity.getProperty("x") - myEntity.getProperty("width")
//
//    if (otherEntity.getProperty("x") <= myX){
//        println ("rightwall")
//        otherEntity.setProperty("x", myX-3)
//    }
//
//    if (otherEntity.getProperty("x") >= myX){
//        println ("leftwall")
//        otherEntity.setProperty("x", myX+3)
//    }
}
