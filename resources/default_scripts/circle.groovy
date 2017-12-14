package scripts

import engine.entities.Entity
import engine.events.Event
import javafx.scene.shape.Circle

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    entity.add(new Circle(20))
}