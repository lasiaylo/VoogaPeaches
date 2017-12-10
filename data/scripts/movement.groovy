package scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (KeyPressEvent) event
    switch(event.keyCode){
        case KeyCode.UP:
            if(entity.getProperty("vy") == 0.1) {
                entity.setProperty("vy", 0)
            } else {
                entity.setProperty("vy", -0.1)
            }
            break
        case KeyCode.DOWN:
            if(entity.getProperty("vy") == -0.1){
                entity.setProperty("vy",0)
            } else {
                entity.setProperty("vy", 0.1)
            }
            break
        case KeyCode.LEFT:
            if(entity.getProperty("vx") == 0.1) {
                entity.setProperty("vx", 0)
            } else {
                entity.setProperty("vx", -0.1)
            }
            break
        case KeyCode.RIGHT:
            if(entity.getProperty("vx") == -0.1) {
                entity.setProperty("vx", 0)
            } else {
                entity.setProperty("vx", 0.1)
            }
            break
    }
}