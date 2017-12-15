package filedata.scripts

import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event ->
    ObjectFactory o = new ObjectFactory("PikaText.json", JSONDataFolders.ENTITY_BLUEPRINT)
    Entity i = o.newObject()
    entity.parent.add(i)
}