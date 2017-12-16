import engine.entities.Entity
import engine.events.CollisionEvent
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (CollisionEvent) event
    try {
        if (event.collidedHitBox.tag.equals("ball")) {
            entity.getParent().remove(entity)
            event.getCollidedWith().getParent().remove(event.getCollidedWith())
        }
    } catch (Exception e) {}
}