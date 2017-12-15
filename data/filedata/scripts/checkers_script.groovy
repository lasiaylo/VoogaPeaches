package filedata.scripts

import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import database.jsonhelpers.JSONHelper
import engine.entities.Entity
import engine.events.Event
import engine.events.InitialImageEvent
import util.math.num.Vector

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    println("Fired")
    Map<String, Object> newParams = new HashMap<>();
    newParams.put("x", entity.getProperty("x"))
    newParams.put("y",entity.getProperty("y"))
    newParams.put("vx", 0.3)
    ObjectFactory o = new ObjectFactory("Cat1.json",JSONDataFolders.ENTITY_BLUEPRINT)
    Entity i = o.newObject()
    println(JSONHelper.JSONForObject(i).toString(4))
    def pos = new Vector((double)entity.getProperty("x"), (double)entity.getProperty("y"))
    def size = new Vector((double)entity.getProperty("width"), (double)entity.getProperty("height"))
    entity.add(i)

}