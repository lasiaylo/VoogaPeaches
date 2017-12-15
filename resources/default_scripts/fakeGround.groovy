import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event ->
    if (entity.getProperty("y") >= bindings.get("ground") && entity.getProperty("isFalling") ){
        entity.setProperty("vy",0)
        entity.setProperty("isFalling",false)}
}