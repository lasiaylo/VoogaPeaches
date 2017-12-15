package default_scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event ->
    KeyPressEvent k = (KeyPressEvent) event;
    if (k.getKeyCode().equals(KeyCode.UP)){
        double r = entity.getNodes().getRotate() + 90.0;
        entity.getNodes().setRotate(r);
    }
}