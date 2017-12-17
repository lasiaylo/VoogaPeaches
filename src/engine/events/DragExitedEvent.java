package engine.events;

import javafx.scene.input.MouseEvent;

/**
 * An event that specifies a drag exiting
 * @author estellehe
 */
public class DragExitedEvent extends Event{
    private boolean isGaming;
    private MouseEvent mouseEvent;

    /**
     * Creates a new DragExitedEvent
     * @param isGaming  specifies whether engine is gaming
     * @param e         MouseEvent related to drag
     */
    public DragExitedEvent(boolean isGaming, MouseEvent e) {
        super(EventType.DRAG_EXITED.getType());
        this.isGaming = isGaming;
        this.mouseEvent = e;
    }

    /**
     * @return  whether or not engine is gaming
     */
    public boolean isGaming() {
        return isGaming;
    }

    /**
     * @return  mouse event related to drag
     */
    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }
}
