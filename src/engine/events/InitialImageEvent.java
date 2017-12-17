package engine.events;

import util.math.num.Vector;

/**
 * Represents an initial image event
 */
public class InitialImageEvent extends Event{
    private Vector myGridSize;
    private Vector myPos;

    /**
     * Creates a new InitialImageEvent
     */
    private InitialImageEvent() {
        super(EventType.INITIAL_IMAGE.getType());
    }

    /**
     * Creates a new InitialImageEvent
     * @param gridSize  gridsize of camera
     * @param pos       position of image
     */
    public InitialImageEvent(Vector gridSize, Vector pos) {
        this();
        myGridSize = gridSize;
        myPos = pos;
    }

    /**
     *
     * @return gridsize of camera
     */
    public Vector getMyGridSize() {
        return myGridSize;
    }

    /**
     *
     * @return  new position of image
     */
    public Vector getMyPos() {
        return myPos;
    }
}
