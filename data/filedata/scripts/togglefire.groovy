import engine.entities.Entity
import engine.events.Event
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode


{ Entity entity, Map<String, Object> bindings, Event event = null ->
    if(((KeyPressEvent) event).getKeyCode() == KeyCode.SPACE)
    entity.setProperty("firing", !((boolean)entity.getProperty("firing")))
}