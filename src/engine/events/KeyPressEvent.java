package engine.events;

import javafx.scene.input.KeyCode;

public class KeyPressEvent extends Event {
    private KeyCode myKeyCode;
    private boolean isGaming = true;

    public KeyPressEvent(KeyCode code) {
        super("key press");
        myKeyCode = code;
    }

    public KeyPressEvent(KeyCode code, boolean gaming) {
        super("key press");
        myKeyCode = code;
        isGaming = gaming;
    }


    public KeyCode getKeyCode() {
        return myKeyCode;
    }

    public boolean getIsGaming() {
        return isGaming;
    }
}
