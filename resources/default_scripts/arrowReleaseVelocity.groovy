import database.fileloaders.ScriptLoader
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.KeyPressEvent
import engine.events.KeyReleaseEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event ->
    KeyReleaseEvent keyEvent = (KeyReleaseEvent) event
    if (keyEvent.getKeyCode().equals(KeyCode.LEFT)) {
        entity.setProperty("vx", 0)
    }
    if (keyEvent.getKeyCode().equals(KeyCode.RIGHT)) {
        entity.setProperty("vx", 0)
    }
    if (keyEvent.getKeyCode().equals(KeyCode.UP)) {
        entity.setProperty("vy", 0)
    }
    if (keyEvent.getKeyCode().equals(KeyCode.DOWN)) {
        entity.setProperty("vy", 0)
    }
}