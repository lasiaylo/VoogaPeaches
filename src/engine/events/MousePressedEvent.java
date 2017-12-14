package engine.events;


import javafx.scene.input.MouseEvent;

public class MousePressedEvent extends Event{
    private MouseEvent mouseEvent;
    private boolean isGaming;
    public MousePressedEvent(boolean isGaming, MouseEvent e) {
        super(EventType.MOUSE_PRESS.getType());
        mouseEvent = e;
        this.isGaming = isGaming;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public boolean isGaming() {
        return isGaming;
    }
}
