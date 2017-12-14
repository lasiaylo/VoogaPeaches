package engine.events;

import engine.entities.Entity;

public class SubstituteEvent extends Event {
    private Entity substituted;
    public SubstituteEvent(Entity substituted) {
        super(EventType.SUBSTITUTE.getType());
    }

    public Entity getSubstituted() {
        return substituted;
    }
}
