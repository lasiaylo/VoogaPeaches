package engine.events;

import util.math.num.Vector;

public class MouseDragEvent extends Event {
    private Vector myStartPos = new Vector(0, 0);
    private Vector myStartSize = new Vector(0, 0);
    private boolean isGaming = true;
    private int[] myMode;

    public MouseDragEvent() {
        super(EventType.MOUSE_DRAG.getType());
    }
    public MouseDragEvent(boolean gaming, int[] mode) {
        this();
        isGaming = gaming;
        myMode = mode;
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

    public int[] getMyMode() {
        return myMode;
    }
}
