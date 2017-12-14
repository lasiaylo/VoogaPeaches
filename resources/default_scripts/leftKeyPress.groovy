package scripts

import database.fileloaders.ScriptLoader
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event = null ->

    entity = (Entity) entity

    entity.on(EventType.KEY_PRESS.getType(), { Event e ->
        KeyPressEvent keyEvent = (KeyPressEvent) e
        if (keyEvent.getKeyCode().equals(KeyCode.LEFT)) {
            for (String action : actions) {
                String code = ScriptLoader.getScript(action)
                evaluate(code)
            }
        }
    })
}