package engine.events;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPressEvent extends Event {
    private KeyCode myKeyCode;
    private boolean isGaming = true;

    public KeyPressEvent(KeyEvent code) {
        this(code.getCode());
    }

    public KeyPressEvent(KeyEvent code, boolean gaming) {
        this(code.getCode(), gaming);
    }

    public KeyPressEvent(KeyCode code) {
        super(EventType.KEY_PRESS.getType());
        myKeyCode = code;
    }

    public KeyPressEvent(KeyCode code, boolean gaming) {
        this(code);
        isGaming = gaming;
    }

    public KeyCode getKeyCode() {
        return myKeyCode;
    }

    public boolean getIsGaming() {
        return isGaming;
    }
}
