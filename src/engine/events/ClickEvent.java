package engine.events;

import javafx.scene.input.MouseButton;

import java.io.InputStream;

public class ClickEvent extends Event {

    private MouseButton myMouseButton;
    private boolean isGaming = true;

    public ClickEvent() {
        super(EventType.CLICK.getType());
    }

    public ClickEvent(MouseButton mouse) {
        this();
        myMouseButton = mouse;
    }

    public ClickEvent(boolean gaming) {
        super(EventType.CLICK.getType());
        isGaming = gaming;
    }

    public MouseButton getMouseButton() {
        return myMouseButton;
    }

    public boolean getIsGaming() {
        return isGaming;
    }


}
