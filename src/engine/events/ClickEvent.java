package engine.events;

import javafx.scene.input.MouseButton;

import java.io.InputStream;

public class ClickEvent extends Event {

    private MouseButton myMouseButton;
    private boolean isGaming = true;
    private int myMode[];
    private InputStream myBGType;

    public ClickEvent(MouseButton mouse) {
        super("click");
        myMouseButton = mouse;
    }

    public ClickEvent(boolean gaming, int[] mode, InputStream BGType) {
        super("key press");
        isGaming = gaming;
        myMode = mode;
        myBGType = BGType;
    }

    public MouseButton getMouseButton() {
        return myMouseButton;
    }

    public boolean getIsGaming() {
        return isGaming;
    }

    public int[] getMyMode() {
        return myMode;
    }

    public InputStream getMyBGType() {
        return myBGType;
    }
}
