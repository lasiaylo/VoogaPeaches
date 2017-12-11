package scripts.util

import engine.entities.Entity
import engine.events.Event
import engine.events.ImageViewEvent
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (KeyPressEvent) event

    fire = { String name ->
        print name
        entity.dispatchEvent(new ImageViewEvent((String) bindings.getOrDefault("state_view_" + name, "")))
    }

    switch (event.keyCode) {
        case [KeyCode.W, KeyCode.UP]:
            fire("up")
            break

        case [KeyCode.A, KeyCode.LEFT]:
            fire("left")
            break

        case [KeyCode.S, KeyCode.DOWN]:
            fire("down")
            break

        case [KeyCode.D, KeyCode.RIGHT]:
            fire("right")
            break

    }
}