package scriptdata

import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event
import javafx.scene.Group

entity = (Entity) entity

entity.on("collision", { Event event ->
    CollisionEvent cEvent = (CollisionEvent) cEvent;
    if(cEvent.getCollidedHitBox().equals("rectangle")) {


    }

})