import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event ->
    def decay = bindings.get("decay")
    def vx = entity.getProperty("vx")

    if (vx <=0){
        entity.setProperty("vx", vx+ decay)
    }
    if (vx >=0){
        entity.setProperty("vx", vx- decay)

    }

}