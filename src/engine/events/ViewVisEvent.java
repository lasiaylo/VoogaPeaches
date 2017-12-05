package engine.events;

public class ViewVisEvent extends Event {
    private boolean bool;
    public ViewVisEvent(boolean bool) {
        super("View Visibility Event");
        this.bool = bool;
    }

    public boolean getBool() {
        return bool;
    }
}
