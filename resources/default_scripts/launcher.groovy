import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event = null ->

    String objectFile = bindings.getOrDefault("type","Cat1.json")
    ObjectFactory o = new ObjectFactory(objectFile, JSONDataFolders.ENTITY_BLUEPRINT)
    Entity newEntity = o.newObject()
    entity.add(newEntity)
    newEntity.setProperty("x",bindings.getOrDefault("x",0))
    newEntity.setProperty("y",bindings.getOrDefault("y",0))
    newEntity.setProperty("vx",bindings.getOrDefault("vx",0))
    newEntity.setProperty("vy",bindings.getOrDefault("vy",0))


}
