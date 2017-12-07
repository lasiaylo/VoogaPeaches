package engine.events;

import util.math.num.Vector;

public class MouseDragEvent extends Event {
    private Vector myStartPos;
    private Vector myStartSize;
    private boolean isGaming = true;
    private int[] myMode;

    public MouseDragEvent() {
        super(EventType.MOUSE_DRAG.getType());
    }
    public MouseDragEvent(Vector startPos, Vector startSize, boolean gaming, int[] mode) {
        this();
        myStartPos = startPos;
        myStartSize = startSize;
        isGaming = gaming;
        myMode = mode;
    }

    public Vector getMyStartSize() {
        return myStartSize;
    }

    public Vector getMyStartPos() {
        return myStartPos;
    }

    public boolean getIsGaming() {
        return isGaming;
    }

    public int[] getMyMode() {
        return myMode;
    }
}
