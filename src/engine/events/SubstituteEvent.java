package engine.events;

import engine.entities.Entity;

/**
 * Specifies an event to substitute on
 */
public class SubstituteEvent extends Event {
    private Entity substituted;

    /**
     * Creates a new substituteevent
     * @param substituted   Entity that was substituted
     */
    public SubstituteEvent(Entity substituted) {
        super(EventType.SUBSTITUTE.getType());
    }

    /**
     * @return  entity that was sbustituted
     */
    public Entity getSubstituted() {
        return substituted;
    }
}
