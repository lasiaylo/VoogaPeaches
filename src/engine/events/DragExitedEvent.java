package engine.events;

import javafx.scene.input.MouseEvent;

public class DragExitedEvent extends Event{
    private boolean isGaming;
    private MouseEvent mouseEvent;
    public DragExitedEvent(boolean isGaming, MouseEvent e) {
        super(EventType.DRAG_EXITED.getType());
        this.isGaming = isGaming;
        this.mouseEvent = e;
    }

    public boolean isGaming() {
        return isGaming;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }
}
