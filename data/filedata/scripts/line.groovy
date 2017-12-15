package scripts

import engine.entities.Entity
import engine.events.Event
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle

{ Entity entity, Map<String, Object> bindings ->
    entity.add(new Rectangle(0.0, 300.0, 800.0, 3.0))
}