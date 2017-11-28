package engine.events;

import engine.entities.Entity;

public abstract class Event {
    private String type;
    private Entity target;

    public Event(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void fire(Entity target) {
        target.dispatchEvent(this);
    }
}
