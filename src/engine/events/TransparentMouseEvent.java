package engine.events;

public class TransparentMouseEvent extends Event {
    private boolean bool;
    public TransparentMouseEvent(boolean bool) {
        super("Transparent Mouse Event");
        this.bool = bool;
    }

    public boolean getBool() {
        return bool;
    }
}
