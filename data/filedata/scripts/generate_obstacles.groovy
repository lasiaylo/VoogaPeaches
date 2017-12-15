package filedata.scripts

import database.jsonhelpers.JSONDataFolders
import database.jsonhelpers.JSONDataManager
import database.jsonhelpers.JSONToObjectConverter
import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    println "fdgdgdfdgd"
    JSONDataManager j = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT)
    JSONToObjectConverter<Entity> m = new JSONToObjectConverter<>(Entity.class)
    Entity obstacle = m.createObjectFromJSON(Entity.class, j.readJSONFile("ObstacleJson.json"))
    entity.add(obstacle)
}