package engine.events;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Represents a key press by user
 * @author Albert
 */
public class KeyPressEvent extends Event {
    private boolean isGaming = true;
    private KeyEvent myEvent;

    /**
     * Creates a new KeyPressevent
     */
    public KeyPressEvent() {
        super(EventType.KEY_PRESS.getType());
    }

    /**
     * Creates a new KeyPress Event
     * @param code  code of key pressed
     */
    public KeyPressEvent(KeyEvent code) {
        this();
        myEvent = code;
    }

    /**
     * Creates a new KeyPressEvent
     * @param event     event of key pressed
     * @param gaming    whether or not the engine is gaming
     */
    public KeyPressEvent(KeyEvent event, boolean gaming) {
        this();
        isGaming = gaming;
        myEvent = event;
    }

    /**
     * Creates a new KeyPressEvent
     * @param event Event of keypress
     * @param code  code of keypress
     * @param gaming    whether or not engine is gaming
     */
    public KeyPressEvent(KeyEvent event, KeyCode code, boolean gaming) {
        this();
        isGaming = gaming;
        myEvent = event;
    }

    /**
     * @return  whether or not engine is gaming
     */
    public boolean getIsGaming() {
        return isGaming;
    }

    /**
     * @return  whether or not engine is gaming
     */
    public KeyEvent getMyEvent() {
        return myEvent;
    }

    /**
     * @return  the code of the event
     */
    public KeyCode getKeyCode() {
        return myEvent.getCode();
    }
}
