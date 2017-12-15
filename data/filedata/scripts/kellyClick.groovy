package filedata.scripts

import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event ->
    entity.getParent().remove(entity)
}