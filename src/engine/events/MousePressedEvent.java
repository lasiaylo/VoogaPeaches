package engine.events;


import javafx.scene.input.MouseEvent;

/**
 * Event representing mouse pressed
 * @author Albert
 * @author estellehe
 */
public class MousePressedEvent extends Event{
    private MouseEvent mouseEvent;
    private boolean isGaming;

    /**
     * Creates a new MousePressedEvent
     * @param isGaming  whether or not engine is gaming
     * @param e         event associated with mouse press
     */
    public MousePressedEvent(boolean isGaming, MouseEvent e) {
        super(EventType.MOUSE_PRESS.getType());
        mouseEvent = e;
        this.isGaming = isGaming;
    }

    /**
     * @return  mouse event associated with press
     */
    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    /**
     * @return  whether or not engine isgaming
     */
    public boolean isGaming() {
        return isGaming;
    }
}
