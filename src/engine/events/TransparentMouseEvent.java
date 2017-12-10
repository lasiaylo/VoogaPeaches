package engine.events;

public class TransparentMouseEvent extends Event {
    private boolean bool;
    public TransparentMouseEvent(boolean bool) {
        super(EventType.TRANSPARENT_MOUSE.getType());
        this.bool = bool;
    }

    public boolean getBool() {
        return bool;
    }
}
