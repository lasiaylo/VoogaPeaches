package engine.events;

/**
 * An event for setting up map
 */
public class MapSetupEvent extends Event{

    /**
     * Creates a new MapSetupEvent
     */
    public MapSetupEvent() {
        super (EventType.MAPSETUP.getType());
    }
}

