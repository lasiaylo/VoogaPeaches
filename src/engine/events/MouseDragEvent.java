package engine.events;

import javafx.scene.input.MouseEvent;
import util.math.num.Vector;

/**
 * Represents an event specifying mouse dragging
 */
public class MouseDragEvent extends Event {
    private Vector myStartPos = new Vector(0, 0);
    private Vector myStartSize = new Vector(0, 0);
    private boolean isGaming = true;
    private MouseEvent event;

    /**
     * Creates a new MouseDrag
     */
    public MouseDragEvent() {
        super(EventType.MOUSE_DRAG.getType());
    }

    /**
     * Creates a new MouseDragEvent
     * @param gaming    whether or not engine isgaming
     * @param event     Mouse event associated with drag
     */
    public MouseDragEvent(boolean gaming, MouseEvent event) {
        this();
        isGaming = gaming;
        this.event = event;
    }

    /**
     * Creates a new MouseDragEvent
     * @param gaming    whether or not engine is gaming
     */
    public MouseDragEvent(boolean gaming) {
        this();
        isGaming = gaming;
    }


    /**
     * @return starting size of entity
     */
    public Vector getMyStartSize() {
        return myStartSize;
    }

    /**
     * @return  starting position of drag
     */
    public Vector getMyStartPos() {
        return myStartPos;
    }

    /**
     * Sets the starting position of the drag
     * @param startPosX     x starting position
     * @param startPosY     y starting position
     */
    public void setMyStartPos(double startPosX, double startPosY) {
        myStartPos.at(0, startPosX);
        myStartPos.at(1, startPosY);
    }

    /**
     * Sets the starting size of the image dragged
     * @param startSizeX     x starting position
     * @param startSizeY    y starting position
     */
    public void setMyStartSize(double startSizeX, double startSizeY) {
        myStartSize.at(0, startSizeX);
        myStartSize.at(1, startSizeY);
    }

    /**
     * @return  whether or not engine is gaming
     */
    public boolean getIsGaming() {
        return isGaming;
    }

    /**
     * @return  get the event associated by mouse drag
     */
    public MouseEvent getEvent() {
        return event;
    }
}
