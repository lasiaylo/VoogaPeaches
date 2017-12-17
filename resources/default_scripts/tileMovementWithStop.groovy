package default_scripts

import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import engine.entities.Entity
import engine.events.Event
import engine.events.ImageViewEvent
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode
import util.math.num.Vector

{ Entity entity, Map<String, Object> bindings, Event event ->
    event = (KeyPressEvent) event

    switch (event.keyCode) {
        case [KeyCode.W, KeyCode.UP]:
            entity.setProperty("vy", - bindings.get("velocity"))
            entity.setProperty("vx", 0)
            break

        case [KeyCode.A, KeyCode.LEFT]:
            entity.setProperty("vx", - bindings.get("velocity"))
            entity.setProperty("vy", 0)
            break

        case [KeyCode.S, KeyCode.DOWN]:
            entity.setProperty("vy", bindings.get("velocity"))
            entity.setProperty("vx", 0)
            break

        case [KeyCode.D, KeyCode.RIGHT]:
            entity.setProperty("vx", bindings.get("velocity"))
            entity.setProperty("vy", 0)
            break

        case [KeyCode.SPACE]:
            entity.setProperty("vx", 0)
            entity.setProperty("vy", 0)
            break
    }
}
