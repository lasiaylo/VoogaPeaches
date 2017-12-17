package engine.events;

/**
 * An event that specifies that the engine shoudl reset
 */
public class ResetEvent extends Event {
    /**
     * Creates a new ResetEvent
     */
    public ResetEvent() {
        super(EventType.RESET.getType());
    }
}
