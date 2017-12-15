package engine.events;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class KeyReleaseEvent extends Event {
    private KeyEvent event;
    private boolean isGaming;
    public KeyReleaseEvent(KeyEvent event, boolean isGaming) {
        super(EventType.RELEASE.getType());
        this.event = event;
        this.isGaming = isGaming;
    }

    public KeyCode getKeyCode() {
        return event.getCode();
    }

    public boolean isGaming() {
        return isGaming;
    }
}
