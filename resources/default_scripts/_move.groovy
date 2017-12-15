package scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.MoveEvent
import engine.events.TickEvent


{ Entity entity, Map<String, Object> bindings, Event event = null ->
    entity = (Entity) entity;
    entity.on(EventType.TICK.getType(), { Event e ->
        vx = (Double) entity.getProperty("vx")
        vy = (Double) entity.getProperty("vy")
        TickEvent tickEvent = (TickEvent) e
        new MoveEvent(vx.doubleValue() * tickEvent.getDt(), vy.doubleValue() * tickEvent.getDt()).fire(entity)
    })
}