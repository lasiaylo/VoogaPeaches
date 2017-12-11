package engine.events;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;

public class ClickEvent extends Event {

    private MouseEvent myMouseEvent;
    private boolean isGaming = true;

    public ClickEvent() {
        super(EventType.CLICK.getType());
    }

    public ClickEvent(MouseEvent mouse) {
        this();
        myMouseEvent = mouse;
    }

    public ClickEvent(boolean gaming, MouseEvent event) {
        super(EventType.CLICK.getType());
        isGaming = gaming;
        myMouseEvent = event;
    }

    public MouseEvent getMouseEvent() {
        return myMouseEvent;
    }

    public boolean getIsGaming() {
        return isGaming;
    }


}
