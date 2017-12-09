package scriptdata

import engine.entities.Entity
import engine.events.AccelerateEvent
import engine.events.Event
import engine.events.EventType
import engine.events.TickEvent

entity = (Entity) entity

print "fsfs"

entity.on(EventType.TICK, { Event event ->
    double gx = (Double) entity.getProperty("gx")
    double gy = (Double) entity.getProperty("gy")
    double mass = (Double) entity.getProperty("mass")

    TickEvent tickEvent = (AccelerateEvent) event
    new AccelerateEvent(gx.doubleValue() / mass.doubleValue() * tickEvent.getDt(), gy.doubleValue() / mass.doubleValue() * tickEvent.getDt()).fire(entity as Entity)
})