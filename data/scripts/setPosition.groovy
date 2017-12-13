package scripts

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.MoveEvent

{ Entity entity, Map<String, Object> bindings, Event event = null ->

    entity = (Entity) entity;

    entity.on(EventType.MOVE.getType(), { Event e ->
        MoveEvent moveEvent = (MoveEvent) e
        entity.setProperty("x", new Double(entity.getProperty("x").doubleValue() + moveEvent.getDx()))
        entity.setProperty("y", new Double(entity.getProperty("y").doubleValue() + moveEvent.getDy()))
        entity.getNodes().relocate(entity.getProperty("x"), entity.getProperty("y"))
        for (HitBox hitBox : entity.getHitBoxes()) {
            hitBox.moveHitBox((double) entity.getProperty("x"), (double) entity.getProperty("y"))
        }
    })
}