package filedata.scripts

import database.ObjectFactory
import engine.entities.Entity
import engine.events.ClickEvent
import engine.events.Event
import engine.events.MousePressedEvent
import util.math.num.Vector

{ Entity entity, Map<String, Object> bindings, Event event ->
    entity.getParent().remove(entity)
}