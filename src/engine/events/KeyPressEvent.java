package engine.events;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPressEvent extends Event {
    private boolean isGaming = true;
    private KeyEvent myEvent;
    private KeyCode keyCode;

    public KeyPressEvent() {
        super(EventType.KEY_PRESS.getType());
    }

    public KeyPressEvent(KeyEvent code) {
        this();
        myEvent = code;
    }

    public KeyPressEvent(KeyEvent event, boolean gaming) {
        this();
        isGaming = gaming;
        myEvent = event;
    }

    public KeyPressEvent(KeyEvent event, KeyCode code, boolean gaming) {
        this();
        isGaming = gaming;
        myEvent = event;
        keyCode = code;
    }

    public boolean getIsGaming() {
        return isGaming;
    }

    public KeyEvent getMyEvent() {
        return myEvent;
    }

    public KeyCode getKeyCode() {
        return myEvent.getCode();
    }
}
