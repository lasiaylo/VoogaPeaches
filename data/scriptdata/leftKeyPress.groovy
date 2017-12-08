package scriptdata

import database.scripthelpers.ScriptLoader
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

entity = (Entity) entity

entity.on(EventType.KEY_PRESS.getType(), { Event e ->
    KeyPressEvent keyEvent = (KeyPressEvent) e
    if(keyEvent.getKeyCode().equals(KeyCode.LEFT)) {
        for(String action : actions) {
            String code = ScriptLoader.stringForFile(action)
            evaluate(code)
        }

    }

})