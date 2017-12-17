package engine.events;

/**
 * Specifies an event to step an fsm
 */
public class StepEvent extends Event {

    /**
     * Create a new stepevent
     */
    public StepEvent() {
        super(EventType.STEP.getType());
    }
}
