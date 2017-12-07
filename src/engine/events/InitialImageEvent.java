package engine.events;

import util.math.num.Vector;

public class InitialImageEvent extends Event{
    private int myGridSize;
    private Vector myPos;

    public InitialImageEvent() {
        super(EventType.INITIAL_IMAGE.getType());
    }

    public InitialImageEvent(int gridSize, Vector pos) {
        this();
        myGridSize = gridSize;
        myPos = pos;
    }

    public int getMyGridSize() {
        return myGridSize;
    }

    public Vector getMyPos() {
        return myPos;
    }
}
