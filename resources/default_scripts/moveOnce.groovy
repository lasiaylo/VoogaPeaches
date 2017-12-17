
package scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.MoveEvent
import engine.events.TickEvent

{Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (TickEvent) event
    println "nomove"
    double vx = (Double) entity.getProperty("vx")
    double vy = (Double) entity.getProperty("vy")
    new MoveEvent((double) vx.doubleValue() * event.getDt(), vy.doubleValue() * event.getDt()).fire(entity)
    entity.setProperty("vx", 0.0)
    entity.setProperty("vy", 0.0)

}
