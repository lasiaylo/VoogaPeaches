package filedata.scripts

import database.ObjectFactory
import engine.entities.Entity
import engine.events.Event
import engine.events.MousePressedEvent
import util.math.num.Vector

{ Entity entity, Map<String, Object> bindings, Event event ->
    MousePressedEvent cEvent = (MousePressedEvent) event
    entity.getNodes().scaleX(2)
    entity.getNodes().scaleY(2)
}