package filedata.scripts

import engine.entities.Entity
import engine.events.ClickEvent
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    System.out.println("testmouse")
    event = (ClickEvent) event
}
