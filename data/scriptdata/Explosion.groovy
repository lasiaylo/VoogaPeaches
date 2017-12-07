package scriptdata

import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event
import engine.events.EventType
import javafx.scene.Group

entity = (Entity) entity

entity.on(EventType.COLLISION.getType(), { Event event ->
    CollisionEvent cEvent = (CollisionEvent) cEvent;
    if(cEvent.getCollidedHitBox().equals("rectangle")) {


    }

})