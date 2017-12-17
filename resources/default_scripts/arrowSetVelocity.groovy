import engine.entities.Entity
import engine.events.Event
import engine.events.KeyPressEvent
import engine.events.KeyReleaseEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event ->
    Event keyEvent
    int i = 1
    double v = 0;
    if (event instanceof KeyReleaseEvent) {
        keyEvent = (KeyReleaseEvent) event
        i *= 0
    } else {
        keyEvent = (KeyPressEvent) event
        v = (double) bindings.get("velocity")
    }
    if (keyEvent.getKeyCode().equals(KeyCode.LEFT)) {
        entity.setProperty("vx", -i*v)
    }
    if (keyEvent.getKeyCode().equals(KeyCode.RIGHT)) {
        entity.setProperty("vx", i*v)
    }
    if (keyEvent.getKeyCode().equals(KeyCode.UP)) {
        entity.setProperty("vy", -i*v)
    }
    if (keyEvent.getKeyCode().equals(KeyCode.DOWN)) {
        entity.setProperty("vy", i*v)
    }
}
