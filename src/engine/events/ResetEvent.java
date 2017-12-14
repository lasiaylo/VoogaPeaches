package engine.events;

public class ResetEvent extends Event {
    public ResetEvent() {
        super(EventType.RESET.getType());
    }
}
