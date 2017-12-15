package scripts

import engine.entities.Entity
import engine.events.Event
import javafx.scene.shape.Circle

{ Entity entity, Map<String, Object> bindings ->
    entity.add(new Circle(bindings.getOrDefault("r", 20.0)))
}