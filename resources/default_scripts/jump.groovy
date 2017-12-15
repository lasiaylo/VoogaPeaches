import engine.entities.Entity
import engine.events.Event
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event ->
    KeyPressEvent keyEvent = (KeyPressEvent) event

    jumpvelocity =(double) bindings.get("jump")

    if (keyEvent.getKeyCode().equals(KeyCode.SPACE)) {
        println "jumping!"
        y = entity.getProperty("y")-10
        entity.setProperty("y",y)
        entity.setProperty("vy", -jumpvelocity)
        entity.setProperty("isFalling",true)
        println entity.getProperty("isFalling")
    }

}