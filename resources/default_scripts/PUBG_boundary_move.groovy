package scripts

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.Event
import engine.events.MoveEvent
import engine.events.TickEvent
import javafx.scene.paint.Color


{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (TickEvent) event
    //vel = 0.1
    boundary = (HitBox) entity.getHitBoxes().get(0)
    box = boundary.getHitbox()
    box.setStroke(Color.BLUE)
    box.setFill(Color.BLUE)
    //box.resize(box.getBoundsInParent().getWidth() - event.getDt() * vel, box.getBoundsInParent().getHeight() - event.getDt() * vel)
}
