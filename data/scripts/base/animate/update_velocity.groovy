import engine.entities.Entity
import engine.events.Event
import engine.events.AccelerateEvent

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (AccelerateEvent) event

    entity.setProperty("vx", ((Double) entity.getProperty("vx")).doubleValue() + event.dvx())
    entity.setProperty("vy", ((Double) entity.getProperty("vy")).doubleValue() + event.dvy())
}
