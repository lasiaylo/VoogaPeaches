package default_scripts

import engine.entities.Entity
import engine.events.AccelerateEvent
import engine.events.CollisionEvent
import engine.events.Event
import engine.events.EventType
import engine.events.TickEvent

{ Entity entity, Map<String, Object> bindings, Event event ->
    CollisionEvent cEvent = (CollisionEvent) event
    if(cEvent.getCollidedHitBox().getTag().equals((String) bindings.get("collided with"))) {



    }

}