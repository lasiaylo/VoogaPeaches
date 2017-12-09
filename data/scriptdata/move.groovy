package scriptdata

import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.MoveEvent
import engine.events.TickEvent

entity = (Entity) entity

print "fsfs"

entity.on(EventType.TICK, { Event event ->
    double vx = (Double) entity.getProperty("vx")
    double vy = (Double) entity.getProperty("vy")

    TickEvent tickEvent = (TickEvent) event
    new MoveEvent(vx.doubleValue() * tickEvent.getDt(), vy.doubleValue() * tickEvent.getDt()).fire(entity as Entity)
})