import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.MoveEvent
import engine.events.TickEvent

entity = (Entity) entity;

entity.on(EventType.TICK.getType(), { Event event ->
    vx = (Double) entity.getProperty("vx")
    vy = (Double) entity.getProperty("vy")

//    println vx
//    println vy
    TickEvent tickEvent = (TickEvent) event
    new MoveEvent(vx.doubleValue() * tickEvent.getDt(), vy.doubleValue() * tickEvent.getDt()).fire(entity)

})