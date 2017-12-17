package default_scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event ->
    KeyPressEvent k = (KeyPressEvent) event;
    switch(k.keyCode){
        case(KeyCode.UP):
            entity.getNodes().setRotate(entity.getNodes().getRotate() + 90.0);
            break;
        case(KeyCode.LEFT):
            entity.setProperty("x", entity.getProperty("x") - 50);
            break;
        case(KeyCode.RIGHT):
            entity.setProperty("x", entity.getProperty("x") + 50);
            break;
        case(KeyCode.DOWN):
            entity.setProperty("y", entity.getProperty("y") + 50);
            break;
    }
}