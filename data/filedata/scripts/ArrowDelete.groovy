package filedata.scripts

import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    if((double) entity.getProperty("x") < 0 ){
        entity.getParent().remove(entity)
    }
}
