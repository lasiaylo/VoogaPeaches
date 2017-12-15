import engine.entities.Entity
import engine.events.Event
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode


{ Entity entity, Map<String, Object> bindings, Event event ->
    KeyPressEvent keyEvent = (KeyPressEvent) event
    println("fired here")
    if (keyEvent.getKeyCode().equals(KeyCode.LEFT)) {
        entity.setProperty("x", entity.getProperty("x") - 50)
    }
    if (keyEvent.getKeyCode().equals(KeyCode.RIGHT)) {
        entity.setProperty("x", entity.getProperty("x") + 50)
    }
    if (keyEvent.getKeyCode().equals(KeyCode.UP)) {
        entity.setProperty("y", entity.getProperty("y") - 50)
    }
    if (keyEvent.getKeyCode().equals(KeyCode.DOWN)) {
        entity.setProperty("y", entity.getProperty("y") + 50)
    }
}