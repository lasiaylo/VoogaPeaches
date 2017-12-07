package engine.events;

import javafx.scene.input.KeyCode;

public class KeyPressEvent extends Event {
    private KeyCode myKeyCode;
    public KeyPressEvent(KeyCode code) {
        super("key press");
        myKeyCode = code;
    }

    private KeyCode getKeyCode() {
        return myKeyCode;
    }
}
