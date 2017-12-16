import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import engine.entities.Entity
import engine.events.Event
import engine.events.TickEvent

{ Entity entity, Map<String, Object> bindings, Event event ->

    TickEvent e = (TickEvent) event

    entity.setProperty("time", entity.getProperty("time") + e.getDt())
    def time = bindings.getOrDefault("wait",300)
    if(((double) entity.getProperty("time") % (16 * time)) == 0) {
        entity.setProperty("time",0.0)
        String objectFile = bindings.getOrDefault("type", "Cat1.json")
        ObjectFactory o = new ObjectFactory(objectFile, JSONDataFolders.ENTITY_BLUEPRINT)
        Entity newEntity = o.newObject()
        entity.parent.add(newEntity)
        println("added")
    }

}
