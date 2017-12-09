package scriptdata

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.MoveEvent

entity = (Entity) entity

entity.on(EventType.MOVE, { Event e ->
    MoveEvent event = (MoveEvent) e

    entity.setProperty("x", ((Double) entity.getProperty("x")).doubleValue() + event.dx())
    entity.setProperty("y", ((Double) entity.getProperty("y")).doubleValue() + event.dy())

    entity.getNodes().relocate(entity.getProperty("x"), entity.getProperty("y"))

    for(HitBox hitBox : entity.getHitBoxes())
        hitBox.moveHitBox((double) entity.getProperty("x"), (double) entity.getProperty("y"))
})
