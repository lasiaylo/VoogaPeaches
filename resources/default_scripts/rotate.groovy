package default_scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.ImageViewEvent
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

    { Entity entity, Map<String, Object> bindings, Event event ->
        event = (KeyPressEvent) event

        fire = { String name ->
            print name
            entity.dispatchEvent(new ImageViewEvent((String) bindings.getOrDefault("state_view_" + name, "")))
        }

        switch (event.keyCode) {
            case [KeyCode.KP_RIGHT, KeyCode.UP]:
                fire("up")
                break

            case [KeyCode.KP_LEFT, KeyCode.LEFT]:
                fire("left")
                break
        }
    }