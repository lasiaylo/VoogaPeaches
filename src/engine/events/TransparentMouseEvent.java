package engine.events;

/**
 * Event that specifies that mouse should be transparent
 * @author estellehe
 */
public class TransparentMouseEvent extends Event {
    private boolean bool;

    /**
     * Creates a new TransparentMouseEvent
     * @param bool  whether or not mouse should be transparent
     */
    public TransparentMouseEvent(boolean bool) {
        super(EventType.TRANSPARENT_MOUSE.getType());
        this.bool = bool;
    }

    /**
     * @return  whether or not mouse should be transparent
     */
    public boolean getBool() {
        return bool;
    }
}
