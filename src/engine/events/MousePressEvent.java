package engine.events;

import util.math.num.Vector;

public class MousePressEvent extends Event{
    private Vector myStartPos;
    private Vector myStartSize;
    private boolean isGaming = false;
    private int[] myMode;

    public MousePressEvent() {
        super("mouse press");
    }

    public MousePressEvent(Vector startPos, Vector startSize, boolean gaming, int[] mode) {
        this();
        myStartPos = startPos;
        myStartSize = startSize;
        isGaming = gaming;
        myMode = mode;
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
