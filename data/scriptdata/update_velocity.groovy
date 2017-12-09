package scriptdata

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.AccelerateEvent
import engine.events.Event
import engine.events.EventType
import engine.events.MoveEvent

entity = (Entity) entity

entity.on(EventType.ACCELERATE, { Event e ->
    AccelerateEvent event = (AccelerateEvent) e

    entity.setProperty("vx", ((Double) entity.getProperty("vx")).doubleValue() + event.dvx())
    entity.setProperty("vy", ((Double) entity.getProperty("vy")).doubleValue() + event.dvy())
})
