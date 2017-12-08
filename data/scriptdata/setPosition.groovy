import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.MoveEvent

entity = (Entity) entity;

entity.on(EventType.MOVE.getType(), { Event event ->
    MoveEvent moveEvent = (MoveEvent) event

    entity.setProperty("x", new Double(entity.getProperty("x").doubleValue() + moveEvent.getDx() ))
    entity.setProperty("y", new Double(entity.getProperty("y").doubleValue() + moveEvent.getDy() ))

//    println(moveEvent.getDx())
//    println moveEvent.getDy()

    entity.getNodes().relocate(entity.getProperty("x"), entity.getProperty("y"))

    for(HitBox hitBox : entity.getHitBoxes()) {
        hitBox.moveHitBox((double) entity.getProperty("x"), (double) entity.getProperty("y"))
    }

})

