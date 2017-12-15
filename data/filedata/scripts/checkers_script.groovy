package filedata.scripts

import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import engine.entities.Entity
import engine.events.Event
import util.math.num.Vector

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    ObjectFactory o = new ObjectFactory("Cat1.json",JSONDataFolders.ENTITY_BLUEPRINT)
    Entity i = o.newObject()
    def pos = new Vector((double)entity.getProperty("x"), (double)entity.getProperty("y"))
    def size = new Vector((double)entity.getProperty("width"), (double)entity.getProperty("height"))
    entity.parent.add(i)
    i.setProperty("vx", 0.2)
    i.setProperty("x",entity.getProperty("x") + 50)

}