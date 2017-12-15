import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event = null ->


    def yv =(double) entity.getProperty("vy")
    def fall = bindings.get("fallspeed")

    if ((yv <= bindings.get("MaxFallSpeed"))) {
        entity.setProperty("vy", yv + fall)
        println yv
    }
}