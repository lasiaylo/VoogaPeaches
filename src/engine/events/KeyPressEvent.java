package engine.events;

import javafx.scene.input.KeyCode;

public class KeyPressEvent extends Event {
    private KeyCode myKeyCode;
    private boolean isGaming = true;

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
