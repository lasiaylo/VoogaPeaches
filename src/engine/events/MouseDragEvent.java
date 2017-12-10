package engine.events;

import javafx.scene.input.MouseEvent;
import util.math.num.Vector;

public class MouseDragEvent extends Event {
    private Vector myStartPos = new Vector(0, 0);
    private Vector myStartSize = new Vector(0, 0);
    private boolean isGaming = true;
    private MouseEvent event;

    public MouseDragEvent() {
        super(EventType.MOUSE_DRAG.getType());
    }
    public MouseDragEvent(boolean gaming, MouseEvent event) {
        this();
        isGaming = gaming;
        this.event = event;
    }

    public Vector getMyStartSize() {
        return myStartSize;
    }

    public Vector getMyStartPos() {
        return myStartPos;
    }

    public void setMyStartPos(double startPosX, double startPosY) {
        myStartPos.at(0, startPosX);
        myStartPos.at(1, startPosY);
    }

    public void setMyStartSize(double startSizeX, double startSizeY) {
        myStartSize.at(0, startSizeX);
        myStartSize.at(1, startSizeY);
    }

    public boolean getIsGaming() {
        return isGaming;
    }

    public MouseEvent getEvent() {
        return event;
    }
}
