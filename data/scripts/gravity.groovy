import engine.entities.Entity
import engine.events.AccelerateEvent
import engine.events.Event
import engine.events.TickEvent

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (TickEvent) event

    double gx = (Double) entity.getProperty("gx")
    double gy = (Double) entity.getProperty("gy")
    double mass = (Double) entity.getProperty("mass")

    new AccelerateEvent(gx.doubleValue() / mass.doubleValue() * event.getDt(),
            gy.doubleValue() / mass.doubleValue() * event.getDt()).fire(entity)
}