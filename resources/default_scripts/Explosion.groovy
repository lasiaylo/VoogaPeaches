package scripts

import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event
import engine.events.EventType
import javafx.scene.Group

{ Entity entity, Map<String, Object> bindings, Event event = null ->

    entity = (Entity) entity
    param = (String) param

    entity.on(EventType.COLLISION.getType(), { Event e ->
        CollisionEvent cEvent = (CollisionEvent) e;
        if (cEvent.getCollidedHitBox().equals(param)) {
        }
    })
}