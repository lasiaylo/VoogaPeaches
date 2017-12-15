package scripts

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.Event
import engine.events.MoveEvent

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (MoveEvent) event
    if(((Double) entity.getProperty("x")).doubleValue() + event.dx())
        entity.setProperty("x", ((Double) entity.getProperty("x")).doubleValue() + event.dx())
    if(((Double) entity.getProperty("y")).doubleValue() + event.dy() > 0)
        entity.setProperty("y", ((Double) entity.getProperty("y")).doubleValue() + event.dy())
    entity.getNodes().relocate(((Double) entity.getProperty("x")).doubleValue(),
            ((Double) entity.getProperty("y")).doubleValue())
    for (HitBox hitBox : entity.getHitBoxes())
        hitBox.moveHitBox((double) entity.getProperty("x"), (double) entity.getProperty("y"))
}
