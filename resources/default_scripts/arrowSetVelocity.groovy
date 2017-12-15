import database.fileloaders.ScriptLoader
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event ->
    KeyPressEvent keyEvent = (KeyPressEvent) event
    println "key pressed"
    if (keyEvent.getKeyCode().equals(KeyCode.LEFT)) {
        println "left"
        entity.setProperty("vx", -bindings.get("velocity"))
    }
    if (keyEvent.getKeyCode().equals(KeyCode.RIGHT)) {
        println "right"
        entity.setProperty("vx", bindings.get("velocity"))
    }
    if (keyEvent.getKeyCode().equals(KeyCode.UP)) {
        println "up"
        entity.setProperty("vy", -bindings.get("velocity"))
    }
    if (keyEvent.getKeyCode().equals(KeyCode.DOWN)) {
        println "down"
        entity.setProperty("vy", bindings.get("velocity"))
    }
}