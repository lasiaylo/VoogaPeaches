import engine.entities.Entity
import engine.events.Event

{Entity entity, Map<String, Object> bindings, Event event = null ->
    double vx = (Double) entity.getProperty("vx")
    double vy = (Double) entity.getProperty("vy")

    new engine.events.MoveEvent(vx.doubleValue() * event.getDt(), vy.doubleValue() * event.getDt()).fire(entity)
}

