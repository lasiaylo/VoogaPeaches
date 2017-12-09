package scriptdata

import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.MoveEvent

entity = (Entity) entity

entity.on(EventType.MOVE, { Event event ->
    event = (MoveEvent) event

    
})