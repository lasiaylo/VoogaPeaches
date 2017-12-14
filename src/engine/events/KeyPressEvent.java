package engine.events;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPressEvent extends Event {
    private KeyCode myKeyCode;
    private boolean isGaming = true;
    private KeyEvent myEvent;

    public KeyPressEvent() {
        super(EventType.KEY_PRESS.getType());
    }
    public KeyPressEvent(KeyCode code) {
        this();
        myKeyCode = code;
    }

    public KeyPressEvent(KeyEvent code) {
        this();
        myEvent = code;
    }

    public KeyPressEvent(KeyEvent event, KeyCode code, boolean gaming) {
        this();
        myKeyCode = code;
        isGaming = gaming;
        myEvent = event;
    }

    public KeyCode getKeyCode() {
        return myKeyCode;
    }

    public boolean getIsGaming() {
        return isGaming;
    }

    public KeyEvent getMyEvent() {
        return myEvent;
    }
}
