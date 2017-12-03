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

    public Event fire(Entity target) {
        target.dispatchEvent(this);
        return this;
    }

    public Event recursiveFire(Entity target) {
        fire(target);

        Entity root = target;
        if(!root.getChildren().hasNext()) {
            return this;
        }

        while (root.getChildren().hasNext())
            recursiveFire(root.getChildren().next());
        return this;
    }
}
