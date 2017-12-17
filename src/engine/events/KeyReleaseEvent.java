package engine.events;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Event that releases KeyReleaseEvent
 * @author estellehe
 */
public class KeyReleaseEvent extends Event {
    private KeyEvent event;
    private boolean isGaming;

    /**
     * Creates a new KeyReleaseEvent
     * @param event     event of key release
     * @param isGaming  whether or not engine is gaming
     */
    public KeyReleaseEvent(KeyEvent event, boolean isGaming) {
        super(EventType.RELEASE.getType());
        this.event = event;
        this.isGaming = isGaming;
    }

    /**
     * @return  key code of event
     */
    public KeyCode getKeyCode() {
        return event.getCode();
    }

    /**
     * @return whether or not engine is gaming
     */
    public boolean isGaming() {
        return isGaming;
    }
}
