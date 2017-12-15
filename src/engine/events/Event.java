package engine.events;

import engine.entities.Entity;

import java.util.Iterator;

public abstract class Event {
    private String type;
    private Entity target;

    public Event(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Event fire(Entity target) {
        target.dispatchEvent(this);
        return this;
    }

    public Event recursiveFire(Entity target) {
        if(target == null) {
            return this;
        }

        fire(target);
        if(target.getChildren().isEmpty() || target.getChildren() == null) {
            return this;
        }

        try {
            Iterator<Entity> iter = target.getChildren().iterator();
            iter.forEachRemaining(e -> recursiveFire(e));
        } catch( Exception e) {}
        return this;
    }
}
