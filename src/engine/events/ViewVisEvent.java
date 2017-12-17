package engine.events;

/**
 * Event that specifies whether or not an imageview should be visible
 * @author estellehe
 */
public class ViewVisEvent extends Event {
    private boolean bool;

    /**
     * creates a new ViewVisEvent
     * @param bool  whether or not imageview should be invisible
     */
    public ViewVisEvent(boolean bool) {
        super(EventType.VIEWVIS.getType());
        this.bool = bool;
    }

    /**
     * @return  whether or not view should be visible
     */
    public boolean getBool() {
        return bool;
    }
}
