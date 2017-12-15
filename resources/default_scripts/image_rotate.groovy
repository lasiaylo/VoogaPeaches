package default_scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.ImageViewEvent
import engine.events.KeyPressEvent
import engine.events.RotateEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event ->
    event = (KeyPressEvent) event

    println "received press"

    switch (event.keyCode) {
        case [KeyCode.W, KeyCode.UP]:
            println "w"
            entity.getNodes().setRotate(270)
            break

        case [KeyCode.A, KeyCode.LEFT]:
            entity.getNodes().setRotate(180)
            break

        case [KeyCode.S, KeyCode.DOWN]:
            entity.getNodes().setRotate(90)
            break

        case [KeyCode.D, KeyCode.RIGHT]:
            entity.getNodes().setRotate(0)
            break

    }
}