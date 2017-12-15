package default_scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

    { Entity entity, Map<String, Object> bindings, Event event ->
        entity = (Entity) entity;
        entity.on(EventType.KEY_PRESS.getType(), {Event e ->
            KeyPressEvent k = KeyPressEvent e
            if (k.getKeyCode().equals(KeyCode.KP_RIGHT)){
                entity.getNodes().setRotate(entity.getNodes().getRotate() + 90);
            }
            else if (k.getKeyCode().equals(KeyCode.KP_LEFT)){
                entity.getNodes().setRotate(entity.getNodes().getRotate() - 90);
            }
        })
    }