package engine.events;

import util.math.num.Vector;

public class InitialImageEvent extends Event{
    private Vector myGridSize;
    private Vector myPos;

    public InitialImageEvent() {
        super(EventType.INITIAL_IMAGE.getType());
    }

    public InitialImageEvent(Vector gridSize, Vector pos) {
        this();
        myGridSize = gridSize;
        myPos = pos;
    }

    public Vector getMyGridSize() {
        return myGridSize;
    }

    public Vector getMyPos() {
        return myPos;
    }
}
