package filedata.scripts

import engine.entities.Entity
import engine.events.ClickEvent
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event ->

    //ClickEvent click = (ClickEvent) event

    String type = entity.getProperty("type")
    if (type.equals("mine")) {
        // Clicked on mine -> Game Over :(
        for (Entity covers : entity.getParent().getChildren()) {
            if (covers.getProperty("type").equals("cover")) {
                entity.getParent().remove(covers)
            }
        }

    } else if (type.equals("flag")) {
        // Cover over flag
        entity.getParent().remove(entity)
    } else {
        // Regular panel -> hide yourself
        for (Entity covers : entity.getParent().getChildren()) {
            if (covers.getProperty("type").equals("cover")) {
                entity.getParent().remove(covers)
            }
        }

    }

}
