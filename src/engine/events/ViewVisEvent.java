package engine.events;

public class ViewVisEvent extends Event {
    private boolean bool;
    public ViewVisEvent(boolean bool) {
        super(EventType.VIEWVIS.getType());
        this.bool = bool;
    }

    public boolean getBool() {
        return bool;
    }
}
