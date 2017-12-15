import engine.entities.Entity
import engine.events.Event
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event ->
    KeyPressEvent keyEvent = (KeyPressEvent) event
    println "key pressed"
    if (keyEvent.getKeyCode().equals(KeyCode.A)) {
        println "left"
        entity.setProperty("vx", -bindings.get("velocity"))
    }
    if (keyEvent.getKeyCode().equals(KeyCode.D)) {
        println "right"
        entity.setProperty("vx", bindings.get("velocity"))
    }
    if (keyEvent.getKeyCode().equals(KeyCode.W)) {
        println "up"
        entity.setProperty("vy", -bindings.get("velocity"))
    }
    if (keyEvent.getKeyCode().equals(KeyCode.S)) {
        println "down"
        entity.setProperty("vy", bindings.get("velocity"))
    }
}